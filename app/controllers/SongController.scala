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
      "original" -> list(text),
      "file" -> text,
      "sources" -> list(text),
      "id" -> default(number, -1)
    )(Song.apply)(Song.unapply)
  )

  implicit val songRW = upickle.default.macroRW[Song]
  val songsPath = os.pwd / "public" / "songs.json"

  def listSongs() = Action { implicit request: Request[AnyContent] =>
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
        val file = os.read(songsPath)
        val songs = upickle.default.read[List[Song]](file)

        // Adding new song
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.sources, songs.maxBy(_.id).id + 1)
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

    val file = os.read(songsPath)
    val songs = upickle.default.read[List[Song]](file)

    songs.find(_.id == id) match {
      case Some(song) => {
        val filledForm = songForm.fill(song);
        Ok(views.EditSongPage(filledForm, id).page())
      }
      case None => {
        Redirect(routes.SongController.listSongs()).flashing("error" -> "Song not found. Unable to edit.")
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
        val file = os.read(songsPath)
        val songs = upickle.default.read[List[Song]](file)

        // Updating particular song
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.sources, id)
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

  def deleteSong(id: Int) = Action { implicit request =>

    // Retrieving list of songs
    val file = os.read(songsPath)
    val songs = upickle.default.read[List[Song]](file)

    // Deleting particular song
    val (deletedSong, newSongs) = songs.partition(_.id == id)

    if (deletedSong.isEmpty) {
      Redirect(routes.SongController.listSongs()).flashing("Error" -> "Song not found. Unable to delete")
    }
    else {
      // Writing to json file
      // val json = upickle.default.write(newSongs, indent = 4);
      // os.remove(songsPath)
      // os.write(songsPath, json)
      Redirect(routes.SongController.listSongs()).flashing("success" -> s"Deleted ${deletedSong(0).title}")
    }
  }
}
