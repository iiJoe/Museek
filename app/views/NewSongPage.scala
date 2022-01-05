package views

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import scalatags.Text.all._

case class NewSongPage() extends TemplatePage {

  def pageTitle = "New Song"
  // override def stylesheets = "stylesheets/index.css")
  // override def javascripts = Seq("javascripts/index.js")

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      div(cls := "page-content")(
        form(cls := "ui form")(
          div(cls := "field")(
            label("Circle"),
            input(tpe := "text")
          ),
          div(cls := "field")(
            label("Title"),
            input(tpe := "text")
          ),
          div(cls := "field")(
            label("Original"),
            input(tpe := "text")
          ),
          div(cls := "field")(
            label("Link"),
            input(tpe := "text")
          ),
          button(cls := "ui button", tpe := "submit")("Create")
        )

      )
    )
}
