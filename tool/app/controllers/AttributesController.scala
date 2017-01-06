package controllers

import java.io.Reader
import javax.inject.Inject

import domain.{Ops, ServiceContext}
import models.{Attribute, Field}
import play.api.mvc.Call
import protobuf.core.{Name, NamedAttributes, NamedAttributesOuterClass}

class AttributesController @Inject() (context: ServiceContext) extends NameOpsController[NamedAttributes](context) {
  override protected[this] def builder(): Reader => NamedAttributes = {
    NamedAttributesOuterClass.NamedAttributes.newBuilder().parse(b => NamedAttributes.fromJavaProto(b.build()))
  }

  override protected[this] def ops: Ops[NamedAttributes, Name] = service.attributes

  override protected[this] val getCall: (Name) => Call = routes.AttributesController.get

  override protected[this] val setCall: Call = routes.AttributesController.set()

  override protected[this] val deleteCall: (Name) => Call = routes.AttributesController.delete

  override protected[this] val fieldValues: List[Field[NamedAttributes]] = Attribute.values

  override protected[this] val jsonKey: String = "attributes"
}
