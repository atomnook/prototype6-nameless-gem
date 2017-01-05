package domain

import protobuf.core.{Name, NamedAttributes, NamedElements, NamedStatusEffects}

case class Service(context: ServiceContext) {
  def attributes: Ops[NamedAttributes, Name] = NameOps(context, _.attributes, _.attributes)

  def elements: Ops[NamedElements, Name] = NameOps(context, _.elements, _.elements)

  def statusEffects: Ops[NamedStatusEffects, Name] = NameOps(context, _.statusEffects, _.statusEffects)
}
