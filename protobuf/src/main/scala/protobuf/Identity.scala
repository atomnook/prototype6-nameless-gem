package protobuf

trait Identity[A, B] {
  def id(a: A): B
}
