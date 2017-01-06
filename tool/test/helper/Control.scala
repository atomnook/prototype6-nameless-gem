package helper

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatestplus.play.{OneBrowserPerSuite, OneServerPerSuite}

import scala.concurrent.duration._

trait Control { this: OneServerPerSuite with OneBrowserPerSuite =>
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

  def number(q: String): Assignment[Long] = {
    new Assignment[Long] {
      override def :=(value: Long): Unit = {
        explicitlyWait(q)
        numberField(q).value = value.toString
      }
    }
  }
}
