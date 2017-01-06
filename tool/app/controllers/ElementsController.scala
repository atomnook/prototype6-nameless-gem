package controllers

import java.io.Reader
import javax.inject.Inject

import domain.{Ops, ServiceContext}
import models.{Element, Field}
import play.api.mvc.Call
import protobuf.core.{Name, NamedElements, NamedElementsOuterClass}

class ElementsController @Inject() (context: ServiceContext) extends NameOpsController[NamedElements](context) {
  override protected[this] def builder(): (Reader) => NamedElements = {
    NamedElementsOuterClass.NamedElements.newBuilder().parse(b => NamedElements.fromJavaProto(b.build()))
  }

  override protected[this] def ops: Ops[NamedElements, Name] = service.elements

  override protected[this] val getCall: (Name) => Call = routes.ElementsController.get

  override protected[this] val setCall: Call = routes.ElementsController.set()

  override protected[this] val deleteCall: (Name) => Call = routes.ElementsController.delete

  override protected[this] val fieldValues: List[Field[NamedElements]] = Element.values

  override protected[this] val jsonKey: String = "elements"
}
