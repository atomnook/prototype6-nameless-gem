package domain

import java.util.concurrent.atomic.AtomicReference

import com.trueaccord.lenses.Lens
import protobuf.Database.DatabaseLens
import protobuf.core.Name
import protobuf.{Database, Identity}

class NameOps[A](database: AtomicReference[Database],
                 f: Database => Seq[A],
                 lens: Lens[Database, Seq[A]])
                (implicit val identity: Identity[A, Name]) extends Ops[A, Name] {

  override def list: List[A] = f(database.get()).toList

  override def get(id: Name): Option[A] = list.find(identity.id(_) == id)

  override def set(a: A): Unit = {
    val last = database.get()
    val id = identity.id(a)
    val res = a :: f(last).filterNot(identity.id(_) == id).toList
    database.set(lens.set(res)(last))
  }

  override def delete(id: Name): Unit = {
    val last = database.get()
    val res = list.filterNot(identity.id(_) == id)
    database.set(lens.set(res)(last))
  }
}

object NameOps {
  def apply[A](context: ServiceContext,
               f: Database => Seq[A],
               g: DatabaseLens[Database] => Lens[Database, Seq[A]])
              (implicit identity: Identity[A, Name]): NameOps[A] = {
    new NameOps[A](context.database, f, g(Lens.unit[Database]))
  }
}
