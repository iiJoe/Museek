package views

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import scalatags.Text.all._
import models.Song

case class NewSongPage(songForm: Form[Song])(implicit request: RequestHeader) extends TemplatePage {

  def pageTitle = "New Song"
  override def stylesheets = Seq("stylesheets/newSong.css")

  // TODO figure out close button on error message
  def errMsg: Tag =
    div(cls := "ui error message")(
      i(cls := "close icon"),
      div(cls := "header")(
        "Errors in form submission"
      ),
      ul(cls := "list")(
        songForm.errors.map(err =>
          li(
            err.key, " ",
            err.message, " "
          ),
        )
      )
    )

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      if(songForm.hasErrors) errMsg,
      pForm(controllers.routes.SongController.createSong(), cls := "ui form")(
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
          pInput(songForm("file"))
        ),
        div(cls := "field")(
          label("Source Links"),
          pInput(songForm("sources"))
        ),
        button(cls := "ui button", tpe := "submit")("Create")
      )
    )
}
