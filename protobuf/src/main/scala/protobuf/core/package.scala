package protobuf

package object core {
  private[this] def named[A](f: A => Name): Identity[A, Name] = {
    new Identity[A, Name] {
      override def id(a: A): Name = f(a)
    }
  }

  implicit val nameIdentity: Identity[Name, Name] = named(x => x)

  implicit val namedAttributesIdentity: Identity[NamedAttributes, Name] = named(_.name)

  implicit val namedElementsIdentity: Identity[NamedElements, Name] = named(_.name)

  implicit val namedStatusEffectsIdentity: Identity[NamedStatusEffects, Name] = named(_.name)
}
