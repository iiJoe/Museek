package views

import play.api.mvc._
import scalatags.Text.all._
import models.Song

case class Index(songs: List[Song])(implicit request: RequestHeader, flash: Flash) extends TemplatePage {

  def pageTitle = "Songs List"
  override def stylesheets = Seq("stylesheets/index.css")
  override def javascripts = Seq("javascripts/tablesort.js", "javascripts/index.js")

  def content: Tag =
    div(cls:= "page-container")(
      h1(pageTitle),
      div(cls := "page-content", id := "app", attr("data-name") := "Bob")(

        if (flash.get("success").nonEmpty) div(cls := "ui success message")(
          i(cls := "close icon"),
          flash.get("success").get
        ),

        if (flash.get("error").nonEmpty) div(cls := "ui negative message")(
          i(cls := "close icon"),
          flash.get("error").get
        ),

        div(cls := "ui inverted form search-filter")(
          // TODO for search / accordion for search (?)
          h2(cls := "ui inverted dividing header")("Search"),
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
          div(cls := "songs-header")("No. of Songs: ", songs.length),
          a(cls := "ui button", href := "/newSong")(
            i(cls := "plus square icon"),
            "Add"
          )
        ),

        table(cls := "ui inverted sortable striped table")(
          thead(
            tr(
              th("Circle"),
              th("Title"),
              th("Original"),
              th("Sources"),
              th("Actions")
            )
          ),
          tbody(
            songs.map(song =>
              tr(
                // Delete Song Modal Popup
                div(cls := "ui basic modal delete-modal", id := s"delete-modal-${song.id}")(
                  div(cls := "ui icon header")(
                    i(cls := "trash icon"),
                    "Delete Song"
                  ),
                  div(cls := "content")(
                    p(s"Are you sure you want to delete ${song.title} ?")
                  ),
                  pForm(controllers.routes.SongController.deleteSong(song.id))(
                    div(cls := "actions")(
                      div(cls := "ui red basic cancel inverted button")(
                        i(cls := "remove icon"),
                        "No"
                      ),
                      button(cls := "ui green ok inverted button", tpe := "submit")(
                        i(cls := "checkmark icon"),
                        "Yes"
                      )
                    )
                  )
                ),

                td(if (song.circle.trim.isEmpty) "-" else song.circle),
                td(song.title),
                td(
                  if (song.original.isEmpty) div("-")
                  else song.original.map(div(_))
                ),
                td(
                  if (song.sources.isEmpty) div("-")
                  else song.sources.map(source => a(href := source)("Link"))
                ),
                td(
                  div(cls := "song-btns")(
                    a(cls := "btn btn-edit", href := controllers.routes.SongController.editSong(song.id).url)(
                      i(cls := "edit icon"),
                    ),
                    a(cls := "btn btn-delete", onclick := s"deleteModal(${song.id})")(
                      i(cls := "trash icon"),
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

