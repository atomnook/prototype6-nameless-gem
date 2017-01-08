package controllers

import domain.{Ops, Service, ServiceContext}
import play.api.mvc.{Action, Controller}

abstract class OpsController[A, B](context: ServiceContext) extends Controller with JsonMappingParser[A] with Api {
  protected[this] val service = Service(context)

  protected[this] def ops: Ops[A, B]

  val set: Action[A] = Action(json) { request =>
    ops.set(request.body)
    ok
  }

  def delete(id: B) = Action { _ =>
    ops.delete(id)
    ok
  }
}
