package protobuf

import org.scalacheck.Arbitrary

package object arbitrary {
  def unique[A, B](implicit a: Arbitrary[A], identity: Identity[A, B]): Stream[A] = {
    def rec(last: List[B]): Stream[A] = {
      val head = Stream.continually(a.arbitrary.sample).flatten.filter(v => !last.contains(identity.id(v))).head
      Stream.cons(head, rec(identity.id(head) :: last))
    }
    rec(Nil)
  }
}
