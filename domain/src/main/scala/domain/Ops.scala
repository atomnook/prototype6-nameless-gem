package domain

import protobuf.Identity

trait Ops[A, B] {
  val identity: Identity[A, B]

  def list: List[A]

  def get(id: B): Option[A]

  def set(a: A): Unit

  def delete(id: B): Unit
}
