package views

import play.api.mvc._
import scalatags.Text.all._
import models.Song

case class Index(songs: List[Song]) extends TemplatePage {

  def pageTitle = "Songs List"
  override def stylesheets = Seq("stylesheets/index.css")
  override def javascripts = Seq("javascripts/tablesort.js", "javascripts/index.js")

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
        div(cls := "list-header")(
          div(cls := "")("No. of Songs: ", songs.length),
          a(cls := "ui button", href := "/newSong")(
            i(cls := "plus square icon"),
            "Add"
          )
        ),

        table(cls := "ui sortable striped table")(
          thead(
            tr(
              th("Circle"),
              th("Title"),
              th("Original"),
              th("Links"),
            )
          ),
          tbody(
            songs.map(song =>
              tr(
                td(if (song.circle.trim.isEmpty) "-" else song.circle),
                td(song.title),
                td(
                  if (song.original.trim.isEmpty) "-"
                  else
                    song.original.split(",").map(div(_))
                ),
                td(
                  div(cls := "song-btns")(
                    a(cls := "btn btn-edit", href := controllers.routes.SongController.editSong(song.id).url)(
                      i(cls := "edit icon"),
                    ),
                    a(cls := "btn btn-yt", href := song.ytLink)(
                      i(cls := "youtube icon"),
                    )
                  )
                )
              )
            )
          )
        )
      )
    )
}

