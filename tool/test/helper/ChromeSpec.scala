package helper

import domain.{Service, ServiceContext}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import protobuf.Database

trait ChromeSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with BeforeAndAfterEach with ChromeFactory {
  private[this] val context: ServiceContext = app.injector.instanceOf(classOf[ServiceContext])

  protected[this] val service: Service = Service(context)

  protected[this] val browser: BrowserInfo = ChromeInfo

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    context.database.set(Database())
  }
}
