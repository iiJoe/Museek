package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.io.FileWriter

@Singleton
class SongController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with TagController{

  def newSong() = Action { implicit request: Request[AnyContent] =>

    Ok(views.NewSongPage().page())
  }

  def createSong() {
    // val fw = new FileWriter("a.txt", true)
    // fw.write("test writing thingy\n")
    // fw.close()
  }

  def editSongs() = Action { implicit request: Request[AnyContent] =>

    Ok(views.EditSongsPage().page())
  }
}
