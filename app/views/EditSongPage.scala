package views

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import scalatags.Text.all._
import models.Song

case class EditSongPage(songForm: Form[Song], songId: Int)(implicit request: RequestHeader) extends TemplatePage {

  def pageTitle = "Edit Song"
  override def stylesheets = Seq("stylesheets/newSong.css")

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      pForm(controllers.routes.SongController.updateSong(songId), cls := "ui form")(
        div(cls := "field")(
          label("Circle"),
          pInput(songForm("circle"))
        ),
        div(cls := "field")(
          label("Title"),
          pInput(songForm("title"))
        ),
        div(cls := "field")(
          label("Original"),
          pInput(songForm("original"))
        ),
        div(cls := "field")(
          label("File"),
          pInput(songForm("file"), readonly)
        ),
        div(cls := "field")(
          label("Source Links"),
          pInput(songForm("sources"))
        ),
        button(cls := "ui button", tpe := "submit")("Edit")
      )
    )
}

