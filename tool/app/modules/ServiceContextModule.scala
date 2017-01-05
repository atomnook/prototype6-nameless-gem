package modules

import com.google.inject.AbstractModule
import domain.ServiceContext
import play.api.{Configuration, Environment}

class ServiceContextModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ServiceContext]).toInstance(ServiceContext())
  }
}
