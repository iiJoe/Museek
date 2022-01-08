package views

import controllers._
import play.api._
import play.api.data._
import play.api.mvc._
import play.filters.csrf._
import scalatags.Text.all._
import java.time.LocalDate

trait TemplatePage {

  def pageTitle: String
  def content: Tag
  def stylesheets: Seq[String] = Seq.empty
  def javascripts: Seq[String] = Seq.empty

  private def currentYear = LocalDate.now().getYear()

  def appliedStylesheets = "stylesheets/semantic.min.css" +: stylesheets
  def appliedjavascripts = Seq("javascripts/jquery-3.6.0.min.js", "javascripts/semantic.min.js") ++ javascripts

  def page(): Tag = {
    html(
      head(
        scalatags.Text.tags2.title(pageTitle),
        link(rel := "stylesheet", href := routes.Assets.versioned("stylesheets/template.css").url, tpe := "text/css"),
        appliedStylesheets.map(stylesheet =>
          link(rel := "stylesheet", href := routes.Assets.versioned(stylesheet).url, tpe := "text/css")
        ),
      ),
      body(
        div(cls:= "template")(
          div(cls:= "template-nav")(
            a(cls:= "nav-action", href:= "/")(
              div(cls:= "template-logo"),
              div(cls:= "template-title")("Music")
            ),
            a(cls:= "nav-action", href:= "https://touhou.arrangement-chronicle.com/")("Touhou")
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

        appliedjavascripts.map(js =>
          script(src := routes.Assets.versioned(js).url, tpe := "text/javascript")
        ),
      )
    )
  }


  def csrfToken(implicit request: RequestHeader) =
    CSRF.getToken match {
      case Some(token) => token.value
      case _ => ""
    }


  def pForm(call: Call, attrs: AttrPair*)(body: Modifier*)(implicit request: RequestHeader) = {
    form(action := call.url, method := call.method, attrs)(
      input(tpe := "hidden", name := "csrfToken", value := csrfToken),
      body
    )
  }

  def pInput(field: Field, attrs: AttrPair*) =
    input(tpe := "text", name := field.name, field.value.map(value := _), attrs)

}

