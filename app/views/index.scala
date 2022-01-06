package views

import play.api.mvc._
import scalatags.Text.all._
import scala.io.Source

case class Index() extends TemplatePage {

  def pageTitle = "Songs List"
  override def stylesheets = Seq("stylesheets/index.css")
  override def javascripts = Seq("javascripts/index.js")

  override def vueJS = true

  val home = System.getProperty("user.home")
  val lines =  Source.fromFile(s"$home/Music/songs.txt").getLines().map(toSong).toList

  def toSong(str: String) = {
    val splitStr = str.split("\\|")
    (splitStr(0), splitStr(1), splitStr(2), splitStr(3))
  }

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      div(cls := "page-content", id := "app", attr("data-name") := "Bob")(
        div(cls := "ui form search-filter")(
          // TODO for search / accordion for search (?)
          h2(cls := "ui dividing header")("Search"),
          div(cls := "three fields")(
            div(cls := "field")(
              label("Title"),
              input(tpe := "text", id := "title")
            ),
            div(cls := "field")(
              label("Circle"),
              input(tpe := "text", id := "circle")
            ),
            div(cls := "field")(
              label("Original"),
              input(tpe := "text", id := "original")
            )
          ),
          div(cls := "ui submit button", onclick := "search()")("Search")
        ),
        a(cls := "ui primary button", href := "/newSong")("Add"),
        div(cls := "")("No. of Songs: ", lines.length),

        //TODO use semantic-ui table to sort
        div(cls := "songs-list")(
          div(cls := "songs-header")("Circle"),
          div(cls := "songs-header")("Title"),
          div(cls := "songs-header")("Original"),
          div(cls := "songs-header")("Links"),
          lines.map(song =>
            Seq(
              div(cls := "songs-content")(song._1),
              div(cls := "songs-content")(song._2),
              div(cls := "songs-content")(if (song._3.trim.isEmpty) "-" else song._3),
              div(cls := "songs-content")(
                a(cls := "btn btn-yt", href := "https://www.youtube.com/")(
                  i(cls := "large youtube icon"),
                  // song._4
                )
              )
            )
          )
        ),
        div(cls := "ui button")("Button")
      )
    )
}

