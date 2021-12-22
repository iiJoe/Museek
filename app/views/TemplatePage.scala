package views

import controllers._
import play.api._
import play.api.mvc._
import scalatags.Text.all._
import java.time.LocalDate

trait TemplatePage {

  def pageTitle: String
  def content: Tag
  def stylesheets: Seq[String] = Seq.empty
  def javascripts: Seq[String] = Seq.empty

  private def currentYear = LocalDate.now().getYear()
  def vueJS: Boolean = false

  def page(): Tag = {
    html(
      head(
        scalatags.Text.tags2.title(pageTitle),
        link(rel := "stylesheet", href := routes.Assets.versioned("stylesheets/template.css").url, tpe := "text/css"),
        stylesheets.map(stylesheet =>
          link(rel := "stylesheet", href := routes.Assets.versioned(stylesheet).url, tpe := "text/css")
        ),
      ),
      body(
        div(cls:= "template")(
          div(cls:= "template-nav")(
            a(cls:= "nav-action", href:= "")(
              div(cls:= "template-logo"),
              div(cls:= "template-title")("Canvas")
            ),
            a(cls:= "nav-action", href:= "")("Home"),
            a(cls:= "nav-action", href:= "")("Not Home"),
            a(cls:= "nav-action", href:= "")("About")
          ),
          div(cls:= "template-body")(
            content
          ),
          div(cls:= "template-footer")(
            div(cls:= "template-footer-content")(
              div("Made by Me"),
              div("© 2021 - ", currentYear)
            )
          )
        ),
        //Vue
        if(vueJS) script(src := "https://unpkg.com/vue@3.2.6", tpe := "text/javascript"),

        javascripts.map(js =>
          script(src := routes.Assets.versioned(js).url, tpe := "text/javascript")
        ),
      )
    )
  }
}