package domain

import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers
import protobuf.Database
import protobuf.implicits._

class ServiceContextSpec extends FlatSpec with Checkers {
  it should "export/import database" in {
    check { a: Database =>
      val context = ServiceContext()
      context.database.set(a)

      val base64 = context.port
      val actual = ServiceContext()
      actual.port(base64)

      actual.database.get() == a
    }
  }
}
