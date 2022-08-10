package controllers

import javax.inject._
import java.io.FileWriter
import scala.io.Source
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

@Singleton
class SongController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with TagController{

  val songForm = Form(
    mapping(
      "circle" -> text,
      "title" -> nonEmptyText,
      "original" -> text,
      "file" -> text,
      "ytLink" -> text,
      "id" -> default(number, -1)
    )(Song.apply)(Song.unapply)
  )

  implicit val songRW = upickle.default.macroRW[Song]
  val songsPath = os.pwd / "public" / "songs.json"


  def listSongs() = Action { implicit request: Request[AnyContent] =>
    // TODO Retrieve message from request.body to show success / error message on index page?
    // Look into Se-UI message
    val file = os.read(songsPath)
    val songs = upickle.default.read[List[Song]](file)

    Ok(views.Index(songs).page())
  }

  def newSong() = Action { implicit request =>
    Ok(views.NewSongPage(songForm).page())
  }

  def createSong() = Action { implicit request: Request[AnyContent] =>
    songForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.NewSongPage(formWithErrors).page())
      },
      formData => {
        // Retrieving list of songs
        val file = os.read(os.pwd / "public" / "songs.json")
        val songs = upickle.default.read[List[Song]](file)

        // Adding new song
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.ytLink)
        val newSongs = songs :+ song

        // Writing to json file
        val json = upickle.default.write(newSongs, indent = 4);
        os.remove(songsPath)
        os.write(songsPath, json)

        Redirect(routes.SongController.listSongs())
      }
    )
  }

  def editSong(id: Int) = Action { implicit request: Request[AnyContent] =>

    implicit val songRW = upickle.default.macroRW[Song]

    val file = os.read(os.pwd / "public" / "songs.json")
    val songs = upickle.default.read[List[Song]](file)

    songs.find(_.id == id) match {
      case Some(song) => {
        val filledForm = songForm.fill(song);
        Ok(views.EditSongPage(filledForm, id).page())
      }
      case None => {
        // TODO error message if song not found
        Redirect(routes.SongController.listSongs())
      }
    }
  }

  def updateSong(id: Int) = Action { implicit request =>

    songForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.EditSongPage(formWithErrors, id).page())
      },
      formData => {

        // Retrieving list of songs
        val file = os.read(os.pwd / "public" / "songs.json")
        val songs = upickle.default.read[List[Song]](file)

        // Updating particular song
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.ytLink, id)
        val idx = songs.indexWhere(_.id == id)
        val newSongs = songs.updated(idx, song)

        // Writing to json file
        val json = upickle.default.write(newSongs, indent = 4);
        os.remove(songsPath)
        os.write(songsPath, json)

        Redirect(routes.SongController.listSongs())
      }
    )
  }

  def toSong(tup: (String, Int)) = {
    val (str, id) = tup
    val splitStr = str.split("\\|")
    Song(splitStr(0), splitStr(1), splitStr(2), splitStr(3), splitStr(4), id)
  }
}

