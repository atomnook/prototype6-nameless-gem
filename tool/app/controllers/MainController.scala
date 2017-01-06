package controllers

import javax.inject.Inject

import domain.ServiceContext
import play.api.mvc.{Action, Controller}

class MainController @Inject() (context: ServiceContext) extends Controller {
  val index = Action(_ => Ok(views.html.MainController.index()))
}
