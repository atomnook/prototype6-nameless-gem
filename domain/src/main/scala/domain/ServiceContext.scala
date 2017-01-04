package domain

import java.util.concurrent.atomic.AtomicReference

import protobuf.Database

class ServiceContext(val database: AtomicReference[Database])

object ServiceContext {
  def apply(): ServiceContext = new ServiceContext(new AtomicReference[Database](Database()))
}
