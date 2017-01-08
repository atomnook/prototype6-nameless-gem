package domain

import protobuf.character.{Player, PlayerId}
import protobuf.core.{Name, NamedAttributes, NamedElements, NamedStatusEffects}
import protobuf.{Identity, Label, NameValue}

case class Service(context: ServiceContext) {
  def attributes: Ops[NamedAttributes, Name] = BasicOps(context, _.attributes, _.attributes)

  def elements: Ops[NamedElements, Name] = BasicOps(context, _.elements, _.elements)

  def statusEffects: Ops[NamedStatusEffects, Name] = BasicOps(context, _.statusEffects, _.statusEffects)

  def names(label: Label): Ops[Name, Name] = {
    new Ops[Name, Name] {
      override val identity: Identity[Name, Name] = implicitly

      override def list: List[Name] = context.database.get().names.filter(_.label == label).map(_.name).toList

      override def get(id: Name): Option[Name] = {
        context.database.get().names.filter(_.label == label).find(_.name == id).map(_.name)
      }

      private[this] def modify(id: Name)(f: Seq[NameValue] => Seq[NameValue]): Unit = {
        val last = context.database.get()
        val res = last.update(_.names := f(last.names.filterNot(n => n.label == label && n.name == id)))
        context.database.set(res)
      }

      override def set(a: Name): Unit = modify(a)(last => last :+ NameValue().update(_.label := label, _.name := a))

      override def delete(id: Name): Unit = modify(id)(x => x)
    }
  }

  def players: Ops[Player, PlayerId] = BasicOps(context, _.players, _.players)
}
