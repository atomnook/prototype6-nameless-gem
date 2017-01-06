package controllers

import java.io.Reader

import akka.stream.scaladsl.Sink
import akka.util.ByteString
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.libs.streams.Accumulator
import play.api.mvc.BodyParser
import play.api.mvc.Results._

import scala.concurrent.Future
import scala.io.Source

trait JsonMappingParser[A] {
  protected[this] implicit class BuilderToProto[B <: Message.Builder](b: B) {
    def parse(f: B => A)(json: Reader): A = {
      JsonFormat.parser().merge(json, b)
      f(b)
    }
  }

  protected[this] def builder(): Reader => A

  protected[this] val json: BodyParser[A] = {
    BodyParser { _ =>
      val sink = Sink.fold[ByteString, ByteString](ByteString.empty)((state, bs) => state ++ bs)
      Accumulator(sink).mapFuture { bytes =>
        try {
          val reader = Source.fromInputStream(bytes.iterator.asInputStream).bufferedReader()
          Future.successful(Right(builder()(reader)))
        } catch {
          case e: RuntimeException =>
            val message = e.getMessage
            Future.successful(Left(BadRequest(Json.obj("status" -> 400, "message" -> message))))
        }
      }
    }
  }
}
