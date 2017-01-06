package models

import protobuf.core.NamedElements

sealed abstract class Element(override val label: String, override val value: NamedElements => Long) extends Field[NamedElements]

object Element {
  case object UNASPECTED extends Element("UNASPECTED", _.getElements.unaspected)
  case object FIRE extends Element("FIRE", _.getElements.fire)
  case object ICE extends Element("ICE", _.getElements.ice)
  case object VOLT extends Element("VOLT", _.getElements.volt)
  case object WATER extends Element("WATER", _.getElements.water)
  case object EARTH extends Element("EARTH", _.getElements.earth)
  case object WIND extends Element("WIND", _.getElements.wind)
  case object LIGHT extends Element("LIGHT", _.getElements.light)
  case object DARK extends Element("DARK", _.getElements.dark)

  val values: List[Element] = List(UNASPECTED, FIRE, ICE, VOLT, WATER, EARTH, WIND, LIGHT, DARK)
}
