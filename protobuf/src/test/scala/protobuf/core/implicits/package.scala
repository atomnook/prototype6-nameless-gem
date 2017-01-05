package protobuf.core

import org.scalacheck.{Arbitrary, Gen}
import protobuf.implicits.Stream._

package object implicits {
  private[this] def pos[A](f: Stream[Long] => A): Arbitrary[A] = {
    val positive = Gen.infiniteStream(Gen.chooseNum(0, Int.MaxValue).map(_.toLong))
    Arbitrary(positive.map(f))
  }

  private[this] def named[A, B](f: (Name, A) => B)(implicit a: Arbitrary[A]): Arbitrary[B] = {
    Arbitrary {
      for {
        name <- nameArbitrary.arbitrary
        value <- a.arbitrary
      } yield f(name, value)
    }
  }

  implicit val nameArbitrary: Arbitrary[Name] = Arbitrary(Gen.oneOf(Name.values))

  implicit val attributesArbitrary: Arbitrary[Attributes] = pos(s => (Attributes.apply _).tupled(s.tupled8))

  implicit val elementsArbitrary: Arbitrary[Elements] = pos(s => (Elements.apply _).tupled(s.tupled9))

  implicit val statusEffectsArbitrary: Arbitrary[StatusEffects] = pos(s => (StatusEffects.apply _).tupled(s.tupled4))

  implicit val namedAttributesArbitrary: Arbitrary[NamedAttributes] = {
    named[Attributes, NamedAttributes]((n, a) => NamedAttributes().update(_.name := n, _.attributes := a))
  }

  implicit val namedElementsArbitrary: Arbitrary[NamedElements] = {
    named[Elements, NamedElements]((n, e) => NamedElements().update(_.name := n, _.elements := e))
  }

  implicit val namedStatusEffectsArbitrary: Arbitrary[NamedStatusEffects] = {
    named[StatusEffects, NamedStatusEffects]((n, s) => NamedStatusEffects().update(_.name := n, _.statusEffects := s))
  }
}
