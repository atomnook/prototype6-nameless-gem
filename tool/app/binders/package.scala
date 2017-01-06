import play.api.mvc.PathBindable
import protobuf.core.Name

package object binders {
  implicit val namePathBinder: PathBindable[Name] = {
    new PathBindable[Name] {
      override def unbind(key: String, value: Name): String = value.name

      override def bind(key: String, value: String): Either[String, Name] = {
        Name.fromName(value).map(Right(_)).getOrElse(Left(s"$value undefined"))
      }
    }
  }
}
