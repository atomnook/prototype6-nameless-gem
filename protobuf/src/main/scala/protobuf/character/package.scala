package protobuf

package object character {
  implicit val playerIdentity: Identity[Player, PlayerId] = Identity.apply(_.getId)
}
