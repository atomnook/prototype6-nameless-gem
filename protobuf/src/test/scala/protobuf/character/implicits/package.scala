package protobuf.character

import org.scalacheck.{Arbitrary, Gen}
import protobuf.core.implicits._

package object implicits {
  implicit val playerIdArbitrary: Arbitrary[PlayerId] = Arbitrary(Gen.identifier.map(PlayerId(_)))

  implicit val playerArbitrary: Arbitrary[Player] = {
    Arbitrary {
      for {
        id <- playerIdArbitrary.arbitrary
        name <- Gen.identifier
        race <- nameArbitrary.arbitrary
        cls <- nameArbitrary.arbitrary
      } yield Player().update(_.id := id, _.name := name, _.race := race, _.cls := cls)
    }
  }
}
