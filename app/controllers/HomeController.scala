package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with TagController{

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.Index().page())
  }
}

