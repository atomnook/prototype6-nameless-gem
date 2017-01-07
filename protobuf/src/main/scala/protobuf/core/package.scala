package protobuf

package object core {
  implicit val nameIdentity: Identity[Name, Name] = Identity.apply(x => x)

  implicit val namedAttributesIdentity: Identity[NamedAttributes, Name] = Identity.apply(_.name)

  implicit val namedElementsIdentity: Identity[NamedElements, Name] = Identity.apply(_.name)

  implicit val namedStatusEffectsIdentity: Identity[NamedStatusEffects, Name] = Identity.apply(_.name)
}
