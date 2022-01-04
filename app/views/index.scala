package views

import play.api._
import play.api.mvc._
import scalatags.Text.all._
import scala.io.Source

case class Index() extends TemplatePage {

  def pageTitle = "Songs List"
  override def stylesheets = semanticUiCSS +: Seq("stylesheets/index.css")
  override def javascripts = Seq("javascripts/index.js")

  override def vueJS = true

  val lines =  Source.fromFile("a.txt").getLines().map(toSong).toList

  def toSong(str: String) = {
    val splitStr = str.split(",")
    (splitStr(0), splitStr(1), splitStr(2))
  }

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      div(cls := "page-content", id := "app", attr("data-name") := "Bob")(
        // div(id := "counter")(
        //   "Counter: {{ counter }}"
        // ),
        // div(cls :="btn-click", onclick:="btnClick()")("Don't Click"),
        // div(id := "event-handling")(
        //   p("{{ message }}"),
        //   div(cls:="btn-click", attr("v-on:click") := "reverseMessage()")("Text")
        // ),

        div(cls := "ui form search-filter")(
          // TODO for search
          h2(cls := "ui dividing header")("Search"),
          div(cls := "two fields")(
            div(cls := "field")(
              label("Title"),
              input(tpe := "text", id := "title")
            ),
            div(cls := "field")(
              label("Circle"),
              input(tpe := "text", id := "circle")
            )
          ),
          div(cls := "ui submit button", onclick := "search()")("Submit")
        ),
        div(cls := "")("No. of Songs: ", lines.length),
        div(cls := "songs-list")(
          div(cls := "songs-header")("Circle"),
          div(cls := "songs-header")("Title"),
          div(cls := "songs-header")("Original"),
          div(cls := "songs-header")("Links"),
          lines.map(song =>
            Seq(
              div(cls := "songs-content", attr("data-name") := song._3)(song._1),
              div(cls := "songs-content", attr("data-name") := song._3)(song._2),
              div(cls := "songs-content", attr("data-name") := song._3)(
                button(cls := "ui youtube button", href := "")(
                  i(cls := "youtube icon"),
//                  song._3
                )
              )
            )
          )
        ),
        div(cls := "ui button")("Button")
      )
    )
}

