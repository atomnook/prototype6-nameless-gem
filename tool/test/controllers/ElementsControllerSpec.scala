package controllers

import com.trueaccord.lenses.Lens
import domain.Ops
import models.{Element, Field}
import play.api.mvc.Call
import protobuf.core.implicits._
import protobuf.core.{Name, NamedElements}

class ElementsControllerSpec extends NameOpsControllerSpec[NamedElements] {
  override protected[this] def list: Call = routes.ElementsController.list()

  override protected[this] def get(name: Name): Call = routes.ElementsController.get(name)

  override protected[this] def ops: Ops[NamedElements, Name] = service.elements

  override protected[this] def lens: Lens[NamedElements, Name] = Lens.unit[NamedElements].name

  override protected[this] def fields: List[Field[NamedElements]] = Element.values
}
