package domain

trait Ops[A, B] {
  def list: List[A]

  def get(id: B): Option[A]

  def set(a: A): Unit

  def delete(id: B): Unit
}
