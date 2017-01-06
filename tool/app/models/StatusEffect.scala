package models

import protobuf.core.NamedStatusEffects

sealed abstract class StatusEffect(override val label: String, override val value: NamedStatusEffects => Long) extends Field[NamedStatusEffects]

object StatusEffect {
  case object POISON extends StatusEffect("POISON", _.getStatusEffects.poison)
  case object BLIND extends StatusEffect("BLIND", _.getStatusEffects.blind)
  case object SILENCE extends StatusEffect("SILENCE", _.getStatusEffects.silence)
  case object SLEEP extends StatusEffect("SLEEP", _.getStatusEffects.sleep)

  val values: List[StatusEffect] = List(POISON, BLIND, SILENCE, SLEEP)
}
