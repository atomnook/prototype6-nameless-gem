package controllers

import com.trueaccord.lenses.Lens
import domain.Ops
import helper.{ChromeSpec, Control}
import models.Field
import org.scalacheck.Arbitrary
import play.api.mvc.Call
import protobuf.Identity
import protobuf.arbitrary.unique
import protobuf.core.Name
import protobuf.implicits.Stream._

abstract class NameOpsControllerSpec[A : Arbitrary](implicit identity: Identity[A, Name]) extends ChromeSpec with Control {
  protected[this] def list: Call

  protected[this] def get(name: Name): Call

  protected[this] def ops: Ops[A, Name]

  protected[this] def lens: Lens[A, Name]

  protected[this] def fields: List[Field[A]]

  private[this] implicit class Id(a: A) {
    def name: Name = identity.id(a)
  }

  list.url must {
    s"toggle names ${browser.name}" in {
      go to list.absoluteUrl

      val as = unique[A, Name].take(4)

      assert(ops.list === Nil)

      as.foreach { a =>
        click(a.name.name)
        reloadPage()
      }

      eventually {
        assert(ops.list.map(_.name).toSet === as.map(_.name).toSet)
      }

      as.foreach { a =>
        click(a.name.name)
        reloadPage()
      }

      eventually {
        assert(ops.list === Nil)
      }
    }

    s"place link ${browser.name}" in {
      go to list.absoluteUrl

      val a = unique[A, Name].head

      click(a.name.name)

      reloadPage()

      click on linkText(a.name.name)

      eventually {
        assert(currentUrl === get(a.name).absoluteUrl)
      }
    }
  }

  get(Name.NAMELESS).url must {
    s"apply input fields ${browser.name}" in {
      val (a, updated) = unique[A, Name].tupled2
      val expected = lens.set(identity.id(a))(updated)

      ops.set(a)

      go to get(a.name).absoluteUrl

      fields.foreach { b =>
        number(b.id) := b.value(expected)
      }

      assert(ops.list === a :: Nil)

      click("apply")

      eventually {
        assert(ops.list === expected :: Nil)
      }
    }
  }
}
