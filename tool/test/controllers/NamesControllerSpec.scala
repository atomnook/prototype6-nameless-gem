package controllers

import helper.{ChromeSpec, Control}
import protobuf.Label
import protobuf.arbitrary.unique
import protobuf.core.Name
import protobuf.core.implicits._

class NamesControllerSpec extends ChromeSpec with Control {
  private[this] def ops(label: Label) = service.names(label)

  "/names" must {
    s"toggle names ${browser.name}" in {
      Label.values.map { label =>
        go to routes.NamesController.list(label).absoluteUrl

        val as = unique[Name, Name].take(3)
        val o = ops(label)

        assert(o.list === Nil)

        as.foreach { a =>
          click(a.name)
          reloadPage()
        }

        eventually {
          assert(o.list.toSet === as.toSet)
        }

        (label, as)
      }.foreach { case (label, as) =>
        go to routes.NamesController.list(label).absoluteUrl

        val o = ops(label)

        assert(o.list.toSet === as.toSet)

        as.foreach { a =>
          click(a.name)
          reloadPage()
        }

        eventually {
          assert(o.list === Nil)
        }
      }
    }
  }
}
