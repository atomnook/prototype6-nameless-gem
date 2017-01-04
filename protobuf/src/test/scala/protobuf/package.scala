import org.scalacheck.{Arbitrary, Gen}
import protobuf.core._

package object protobuf {
  private[this] def listOf[A](implicit a: Arbitrary[A]): Gen[List[A]] = Gen.listOf(a.arbitrary)

  private[this] implicit val nameValueArbitrary: Arbitrary[NameValue] = {
    Arbitrary {
      for {
        label <- Gen.oneOf(Label.values)
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
