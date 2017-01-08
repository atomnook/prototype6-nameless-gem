package domain

import java.util.Base64
import java.util.concurrent.atomic.AtomicReference

import protobuf.Database

class ServiceContext(val database: AtomicReference[Database]) {
  def port: String = Base64.getEncoder.encodeToString(database.get().toByteArray)

  def port(base64: String): Unit = database.set(Database.parseFrom(Base64.getDecoder.decode(base64)))
}

object ServiceContext {
  def apply(): ServiceContext = new ServiceContext(new AtomicReference[Database](Database()))
}
