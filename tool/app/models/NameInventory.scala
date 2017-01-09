package models

import models.NameInventory.NameStack
import protobuf.core.Name

case class NameInventory(values: List[Name]) {
  val stacks: List[NameStack] = {
    values.groupBy(_.value).map { case (_, list) =>
      NameStack(value = list.head, amount = list.size)
    }.toList
  }
}

object NameInventory {
  case class NameStack(value: Name, amount: Int)
}
