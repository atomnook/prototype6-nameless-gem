import play.api.mvc.PathBindable
import protobuf.Label
import protobuf.character.PlayerId
import protobuf.core.Name

package object binders {
  private[this] def pathBindable[A](f: A => String, g: String => Option[A]): PathBindable[A] = {
    new PathBindable[A] {
      override def unbind(key: String, value: A): String = f(value)

      override def bind(key: String, value: String): Either[String, A] = {
        g(value).map(Right(_)).getOrElse(Left(s"$value undefined"))
      }
    }
  }

  implicit val namePathBinder: PathBindable[Name] = pathBindable(_.name, Name.fromName)

  implicit val labelPathBinder: PathBindable[Label] = pathBindable(_.name, Label.fromName)

  implicit val playerIdPathBinder: PathBindable[PlayerId] = pathBindable(_.id, s => Some(PlayerId().update(_.id := s)))
}
