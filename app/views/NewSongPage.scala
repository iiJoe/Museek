package views

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.filters.csrf._
import scalatags.Text.all._
import models.Song

case class NewSongPage(songForm: Form[Song])(implicit request: RequestHeader) extends TemplatePage {

  def pageTitle = "New Song"
  override def stylesheets = Seq("stylesheets/newSong.css")
  // override def javascripts = Seq("javascripts/index.js")

  // TODO automate forms with csrf token into a module
  val csrfToken =
    CSRF.getToken match {
      case Some(token) => token.value
      case _ => ""
    }


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
      form(cls := "ui form", action := controllers.routes.SongController.createSong().url, method := "POST")(
        input(tpe := "hidden", name := "csrfToken", value := csrfToken),
        div(cls := "field")(
          label("Circle"),
          input(tpe := "text", name := "circle")
        ),
        div(cls := "field")(
          label("Title"),
          input(tpe := "text", name := "title")
        ),
        div(cls := "field")(
          label("Original"),
          input(tpe := "text", name := "original")
        ),
        div(cls := "field")(
          label("File"),
          input(tpe := "text", name := "file")
        ),
        div(cls := "field")(
          label("Link"),
          input(tpe := "text", name := "yLink")
        ),
        button(cls := "ui button", tpe := "submit")("Create")
      )
    )
}
