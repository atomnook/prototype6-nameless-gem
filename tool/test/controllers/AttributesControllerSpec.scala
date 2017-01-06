package controllers

import com.trueaccord.lenses.Lens
import domain.Ops
import models.{Attribute, Field}
import play.api.mvc.Call
import protobuf.core.implicits._
import protobuf.core.{Name, NamedAttributes}

class AttributesControllerSpec extends NameOpsControllerSpec[NamedAttributes] {
  override protected[this] def list: Call = routes.AttributesController.list()

  override protected[this] def ops: Ops[NamedAttributes, Name] = service.attributes

  override protected[this] def get(name: Name): Call = routes.AttributesController.get(name)

  override protected[this] def lens: Lens[NamedAttributes, Name] = Lens.unit[NamedAttributes].name

  override protected[this] def fields: List[Field[NamedAttributes]] = Attribute.values
}
