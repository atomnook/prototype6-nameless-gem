package controllers

import java.io.Reader
import javax.inject.Inject

import domain.{Ops, ServiceContext}
import play.api.mvc.Action
import protobuf.Label
import protobuf.character.{Player, PlayerId, PlayerOuterClass}
import views.html

class PlayerController @Inject() (context: ServiceContext) extends OpsController[Player, PlayerId](context) {
  override protected[this] def builder(): (Reader) => Player = {
    PlayerOuterClass.Player.newBuilder().parse(b => Player.fromJavaProto(b.build()))
  }

  override protected[this] def ops: Ops[Player, PlayerId] = service.players

  val list = Action(_ => Ok(html.PlayerController.list(ops.list)))

  val create = Action { _ =>
    val races = service.names(Label.CHARACTER_RACE_NAME).list
    val classes = service.names(Label.CHARACTER_CLASS_NAME).list
    Ok(html.PlayerController.create(races = races, classes = classes))
  }
}
