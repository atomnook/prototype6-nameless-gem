package helper

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatestplus.play.{OneBrowserPerSuite, OneServerPerSuite}
import play.api.mvc.Call

import scala.concurrent.duration._

trait Control { this: OneServerPerSuite with OneBrowserPerSuite =>
  protected[this] implicit class Url(c: Call) {
    def absoluteUrl: String = s"http://localhost:$port" + c.url
  }

  protected[this] trait Assignment[A] {
    def :=(value: A): Unit
  }

  private[this] val timeout: FiniteDuration = 10.seconds

  protected[this] def explicitlyWait(q: String): Unit = {
    val by = id(q).by
    val wait = new WebDriverWait(webDriver, timeout.toSeconds)
    wait.until(ExpectedConditions.visibilityOfElementLocated(by))
    wait.until(ExpectedConditions.presenceOfElementLocated(by))
    assert(id(q).findElement.nonEmpty, s"$q not located")
  }

  def click(q: String): Unit = {
    explicitlyWait(q)
    click on id(q)
  }

  def text(q: String): Assignment[String] = {
    new Assignment[String] {
      override def :=(value: String): Unit = {
        explicitlyWait(q)
        textField(q).value = value
      }
    }
  }

  def number(q: String): Assignment[Long] = {
    new Assignment[Long] {
      override def :=(value: Long): Unit = {
        explicitlyWait(q)
        numberField(q).value = value.toString
      }
    }
  }
}
