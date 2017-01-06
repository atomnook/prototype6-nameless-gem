package models

import protobuf.core.NamedAttributes

sealed abstract class Attribute(override val label: String, override val value: NamedAttributes => Long) extends Field[NamedAttributes]

object Attribute {
  case object HP extends Attribute("HP", _.getAttributes.hp)
  case object MP extends Attribute("MP", _.getAttributes.mp)
  case object STR extends Attribute("STR", _.getAttributes.str)
  case object VIT extends Attribute("VIT", _.getAttributes.vit)
  case object DEX extends Attribute("DEX", _.getAttributes.dex)
  case object AGI extends Attribute("AGI", _.getAttributes.agi)
  case object INT extends Attribute("INT", _.getAttributes.int)
  case object MND extends Attribute("MND", _.getAttributes.mnd)

  val values: List[Attribute] = List(HP, MP, STR, VIT, DEX, AGI, INT, MND)
}
