package controllers

import play.api.http.Status._
import play.api.libs.json.Json
import play.api.mvc.Results._

trait Api {
  def ok = Ok(Json.obj("status" -> OK))
}
