package views

import play.api._
import play.api.mvc._
import scalatags.Text.all._

case class Index() extends TemplatePage {

  def pageTitle = "Main"
  override def stylesheets = Seq("stylesheets/index.css")
  override def javascripts = Seq("javascripts/index.js")

  override def vueJS = true

  def content: Tag =
    div(cls:= "container")(
      h1(pageTitle),
      div(cls := "content", id := "app", attr("data-name") := "Bob")(
        div(id := "counter")(
          "Counter: {{ counter }}"
        ),
        div(cls:="btn-click", onclick:="btnClick()")("Don't Click Me"),
        div(id := "event-handling")(
          p("{{ message }}"),
          div(cls:="btn-click", attr("v-on:click") := "reverseMessage()")("Text")
        )
      )
    )
}

