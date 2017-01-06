package controllers

import java.io.Reader
import javax.inject.Inject

import domain.{Ops, ServiceContext}
import models.{Field, StatusEffect}
import play.api.mvc.Call
import protobuf.core.{Name, NamedStatusEffects, NamedStatusEffectsOuterClass}

class StatusEffectsController @Inject() (context: ServiceContext) extends NameOpsController[NamedStatusEffects](context) {
  override protected[this] def ops: Ops[NamedStatusEffects, Name] = service.statusEffects

  override protected[this] val getCall: (Name) => Call = routes.StatusEffectsController.get

  override protected[this] val setCall: Call = routes.StatusEffectsController.set()

  override protected[this] val deleteCall: (Name) => Call = routes.StatusEffectsController.delete

  override protected[this] val fieldValues: List[Field[NamedStatusEffects]] = StatusEffect.values

  override protected[this] val jsonKey: String = "status_effects"

  override protected[this] def builder(): (Reader) => NamedStatusEffects = {
    NamedStatusEffectsOuterClass.NamedStatusEffects.newBuilder().parse(b => NamedStatusEffects.fromJavaProto(b.build()))
  }
}
