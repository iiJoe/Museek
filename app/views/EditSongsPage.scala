package views

import play.api._
import play.api.mvc._
import scalatags.Text.all._
//import scala.io.Source

case class EditSongsPage() extends TemplatePage {

  def pageTitle = "Edit Songs"
  // override def stylesheets = "stylesheets/index.css")
  // override def javascripts = Seq("javascripts/index.js")

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      div(cls := "page-content")(

      )
    )
}

