package protobuf

import org.scalacheck.{Arbitrary, Gen}
import protobuf.core._
import protobuf.core.implicits._

package object implicits {
  private[this] def listOf[A](implicit a: Arbitrary[A]): Gen[List[A]] = Gen.listOf(a.arbitrary)

  implicit val labelArbitrary: Arbitrary[Label] = Arbitrary(Gen.oneOf(Label.values))

  private[this] implicit val nameValueArbitrary: Arbitrary[NameValue] = {
    Arbitrary {
      for {
        label <- labelArbitrary.arbitrary
        name <- nameArbitrary.arbitrary
      } yield NameValue().update(_.label := label, _.name := name)
    }
  }

  implicit val databaseArbitrary: Arbitrary[Database] = {
    Arbitrary {
      for {
        attributes <- listOf[NamedAttributes]
        elements <- listOf[NamedElements]
        statusEffects <- listOf[NamedStatusEffects]
        names <- listOf[NameValue]
      } yield {
        Database().
          update(_.attributes := attributes, _.elements := elements, _.statusEffects := statusEffects, _.names := names)
      }
    }
  }
}
