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

  val home = System.getProperty("user.home")

  def listSongs() = Action { implicit request: Request[AnyContent] =>
    val songs =  Source.fromFile(s"$home/Music/songs.txt").getLines().zipWithIndex.map(toSong).toList
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
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.ytLink)

        val circle = if (song.circle.trim.nonEmpty) song.circle else " "
        val original = if (song.original.trim.nonEmpty) song.original else " "
        val file = if (song.file.trim.nonEmpty) song.file else " "
        val ytLink = if (song.ytLink.trim.nonEmpty) song.ytLink else " "

        val fw = new FileWriter(s"$home/Music/songs.txt", true)

        fw.write(s"$circle|${song.title}|$original|$file|$ytLink")
        fw.close()
        Redirect(routes.SongController.listSongs())
      }
    )
  }

  def editSong(id: Int) = Action { implicit request: Request[AnyContent] =>
    val songs =  Source.fromFile(s"$home/Music/songs.txt").getLines().zipWithIndex.map(toSong).toList
    val song = songs(id)
    val filledForm = songForm.fill(song);
    Ok(views.EditSongPage(filledForm, id).page())
  }

  def updateSong(id: Int) = Action { implicit request =>

    songForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.EditSongPage(formWithErrors, id).page())
      },
      formData => {

        val songs =  Source.fromFile(s"$home/Music/songs.txt").getLines().zipWithIndex.map(toSong).toList

        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.ytLink, id)

        val circle = if (song.circle.trim.nonEmpty) song.circle else " "
        val original = if (song.original.trim.nonEmpty) song.original else " "
        val file = if (song.file.trim.nonEmpty) song.file else " "
        val ytLink = if (song.ytLink.trim.nonEmpty) song.ytLink else " "

        val fw = new FileWriter(s"$home/Music/songs.txt")

        val (left, right) = songs.splitAt(id)
        ((left) ++ (song :: right.tail))
          .foreach(s => fw.write(s"${s.circle}|${s.title}|${s.original}|${s.file}|${s.ytLink}\n"))
        fw.close()
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

