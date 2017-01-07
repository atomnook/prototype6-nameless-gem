package controllers

import javax.inject.Inject

import domain.{Service, ServiceContext}
import play.api.mvc.{Action, Controller}
import protobuf.Label
import protobuf.core.Name
import views.html

class NamesController @Inject()(context: ServiceContext) extends Controller with Api {
  private[this] val service = Service(context)

  private[this] def ops(label: Label) = service.names(label)

  def list(label: Label) = Action { _ =>
    Ok(html.NamesController.list(available = ops(label).list, label = label))
  }

  def set(label: Label, name: Name) = Action { _ =>
    ops(label).set(name)
    ok
  }

  def delete(label: Label, name: Name)= Action { _ =>
    ops(label).delete(name)
    ok
  }
}
