package controllers

import java.io.Reader
import javax.inject.Inject

import domain.{Ops, ServiceContext}
import models.NameInventory
import play.api.mvc.Action
import protobuf.Label
import protobuf.character.{Player, PlayerId, PlayerOuterClass}
import protobuf.core.Name
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

  def nameInventory(id: PlayerId) = Action { _ =>
    ops.get(id) match {
      case Some(a) =>
        val adminInventory = NameInventory(Name.values.toList)
        Ok(html.PlayerController.nameInventory(admin = adminInventory))

      case None =>
        NotFound(html.ErrorHandler.error(status = NOT_FOUND, s"$id not found"))
    }
  }
}
