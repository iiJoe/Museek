package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import java.io.FileWriter

@Singleton
class SongController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with TagController{

  val songForm = Form(
    mapping(
      "circle" -> text,
      "title" -> nonEmptyText,
      "original" -> text,
      "file" -> text,
      "ytLink" -> text
    )(Song.apply)(Song.unapply)
  )

  def newSong() = Action { implicit request =>
    Ok(views.NewSongPage(songForm).page())
  }

  def createSong() = Action { implicit request: Request[AnyContent] =>
    songForm.bindFromRequest().fold(
      formWithErrors => {
        //formWithErrors.getClass.getMethods.foreach(println)
        println(formWithErrors.errors)
        BadRequest(views.NewSongPage(formWithErrors).page())
      },
      formData => {
        val song = models.Song(formData.circle, formData.title, formData.original, formData.file, formData.ytLink)
        //TODO write form inputs to file
        // val fw = new FileWriter("a.txt", true)
        // fw.write("test writing thingy\n")
        // fw.close()
        Redirect(routes.HomeController.index())
      }
    )
  }

  def editSongs() = Action { implicit request: Request[AnyContent] =>

    Ok(views.EditSongsPage().page())
  }
}
