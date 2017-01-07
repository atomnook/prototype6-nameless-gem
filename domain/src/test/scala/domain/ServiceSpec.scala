package domain

import org.scalacheck.Arbitrary
import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers
import protobuf.arbitrary.unique
import protobuf.character.implicits._
import protobuf.character.{Player, PlayerId}
import protobuf.core.implicits._
import protobuf.core.{Name, NamedAttributes, NamedElements, NamedStatusEffects}
import protobuf.implicits.Stream._
import protobuf.{Identity, Label}

class ServiceSpec extends FlatSpec with Checkers {
  private[this] def test[A : Arbitrary, B](name: String, f: Service => Ops[A, B], update: (A, A) => A)
                                          (implicit identity: Identity[A, B]) = {
    it should s"set and get $name" in {
      check { a: A =>
        val service = Service(ServiceContext())
        f(service).set(a)
        f(service).get(identity.id(a)).contains(a)
      }
    }

    it should s"update $name" in {
      check { (last: A, data: A) =>
        val service = Service(ServiceContext())
        f(service).set(last)

        val expected = update(last, data)
        f(service).set(expected)
        f(service).get(identity.id(last)).contains(expected)
      }
    }

    it should s"delete $name" in {
      check { a: A =>
        val service = Service(ServiceContext())
        f(service).set(a)
        f(service).delete(identity.id(a))
        f(service).list == Nil
      }
    }

    it should s"cons list $name" in {
      val service = Service(ServiceContext())
      val (a, b, c, d) = unique[A, B].tupled4
      val list = a :: b :: c :: d :: Nil
      list.foreach(f(service).set)

      assert(f(service).list.toSet === list.toSet)
    }
  }

  test[NamedAttributes, Name]("attributes", _.attributes, (last, data) => data.update(_.name := last.name))

  test[NamedElements, Name]("elements", _.elements, (last, data) => data.update(_.name := last.name))

  test[NamedStatusEffects, Name]("status effects", _.statusEffects, (last, data) => data.update(_.name := last.name))

  test[Name, Name]("names", _.names(Label.CHARACTER_CLASS_NAME), (last, _) => last)

  test[Player, PlayerId]("players", _.players, (last, data) => data.update(_.id := last.getId))
}
