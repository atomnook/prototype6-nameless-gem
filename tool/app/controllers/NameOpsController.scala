package controllers

import domain.{Ops, Service, ServiceContext}
import models.Field
import play.api.libs.json.Json
import play.api.mvc.{Action, Call, Controller}
import protobuf.core.Name
import views.html

abstract class NameOpsController[A](context: ServiceContext) extends Controller with JsonMappingParser[A] {
  protected[this] type Html = play.twirl.api.HtmlFormat.Appendable

  protected[this] val service = Service(context)

  protected[this] def ops: Ops[A, Name]

  protected[this] val getCall: Name => Call

  protected[this] val setCall: Call

  protected[this] val deleteCall: Name => Call

  protected[this] val fieldValues: List[Field[A]]

  protected[this] val jsonKey: String

  val list = Action { _ =>
    val l = ops.list
    val available = l.map(ops.identity.id)
    val values = l.map(value => (ops.identity.id(value), fieldValues.map(_.fix(value))))
    val toggle = html.NameOpsController.toggle(available = available, set = setCall, remove = deleteCall)
    val table = html.NameOpsController.table(values = values, get = getCall)
    Ok(html.NameOpsController.list(toggle = toggle, table = table))
  }

  def get(name: Name) = Action { _ =>
    ops.get(name) match {
      case Some(a) =>
        val input = html.NameOpsController.input(fields = fieldValues.map(_.fix(a)))
        val json = html.NameOpsController.json(name = name, key = jsonKey, fields = fieldValues)
        Ok(html.NameOpsController.get(input = input, json = json, set = setCall))

      case None =>
        NotFound(html.ErrorHandler.error(status = NOT_FOUND, s"$name not found"))
    }
  }

  private[this] val ok = Ok(Json.obj("status" -> OK))

  val set: Action[A] = Action(json) { request =>
    ops.set(request.body)
    ok
  }

  def delete(name: Name) = Action { _ =>
    ops.delete(name)
    ok
  }
}
