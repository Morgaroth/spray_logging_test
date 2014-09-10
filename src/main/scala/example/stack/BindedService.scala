package example.stack

import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http

trait BindedService {
  this: RESTApi with CoreActors with Core =>

  val port = {
    val conf = ConfigFactory.load()
    conf.getInt("custom.port")
  }

  IO(Http)(system) ! Http.Bind(rootService, "0.0.0.0", port = port)
}
