package controllers

import helper.{ChromeSpec, Control}
import protobuf.Label
import protobuf.arbitrary.unique
import protobuf.character.Player
import protobuf.core.Name
import protobuf.core.implicits._
import protobuf.implicits.Stream._

class PlayerControllerSpec extends ChromeSpec with Control {
  private[this] val create = routes.PlayerController.create()

  create.url must {
    s"create new player${browser.name}" in {
      val name = "Test Name"
      val id = name.toLowerCase.replace(' ', '-')
      val (race, cls) = unique[Name].tupled2

      service.names(Label.CHARACTER_RACE_NAME).set(race)
      service.names(Label.CHARACTER_CLASS_NAME).set(cls)

      go to create.absoluteUrl

      text("name") := "Test Name"

      click(s"race-${race.name}")

      click(s"cls-${cls.name}")

      click("new")

      val expected = Player().update(_.id.id := id, _.name := name, _.race := race, _.cls := cls)
      eventually {
        assert(service.players.list === expected :: Nil)
      }
    }
  }
}
