package protobuf

trait Identity[A, B] {
  def id(a: A): B
}

object Identity {
  def apply[A, B](f: A => B): Identity[A, B] = {
    new Identity[A, B] {
      override def id(a: A): B = f(a)
    }
  }
}
