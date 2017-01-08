package controllers

import java.io.ByteArrayInputStream
import javax.inject.Inject

import akka.stream.scaladsl.StreamConverters
import domain.ServiceContext
import modules.ServiceContextModule
import play.api.mvc.{Action, Controller}

class MainController @Inject() (context: ServiceContext) extends Controller {
  val index = Action(_ => Ok(views.html.MainController.index()))

  val export = Action { _ =>
    val data = () => new ByteArrayInputStream(context.port.getBytes("UTF-8"))
    val source = StreamConverters.fromInputStream(data, 100)
    Ok.chunked(source).
      withHeaders("Content-Disposition" -> s"attachment; filename=${ServiceContextModule.filename}")
  }
}
