package controllers

import com.trueaccord.lenses.Lens
import domain.Ops
import models.{Field, StatusEffect}
import play.api.mvc.Call
import protobuf.core.implicits._
import protobuf.core.{Name, NamedStatusEffects}

class StatusEffectsControllerSpec extends NameOpsControllerSpec[NamedStatusEffects] {
  override protected[this] def list: Call = routes.StatusEffectsController.list()

  override protected[this] def get(name: Name): Call = routes.StatusEffectsController.get(name)

  override protected[this] def ops: Ops[NamedStatusEffects, Name] = service.statusEffects

  override protected[this] def lens: Lens[NamedStatusEffects, Name] = Lens.unit[NamedStatusEffects].name

  override protected[this] def fields: List[Field[NamedStatusEffects]] = StatusEffect.values
}
