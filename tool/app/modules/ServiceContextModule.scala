package modules

import com.google.inject.AbstractModule
import domain.ServiceContext
import play.api.{Configuration, Environment, Logger}

import scala.io.Source

class ServiceContextModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    val enable = configuration.getBoolean("database.init.enable").getOrElse(false)
    val context = ServiceContext()

    if (enable) {
      environment.resourceAsStream(ServiceContextModule.filename) match {
        case Some(stream) =>
          val base64 = Source.fromInputStream(stream, "UTF-8").mkString
          val head = base64.take(100)
          val ellipsis = if (base64.length > 100) "..." else ""
          Logger.info(s"database($head$ellipsis) (${base64.length})")

          context.port(base64)

        case None =>
          Logger.info(s"${ServiceContextModule.filename} not found")
      }
    }

    bind(classOf[ServiceContext]).toInstance(context)
  }
}

object ServiceContextModule {
  val filename = "database-utf8-base64.txt"
}
