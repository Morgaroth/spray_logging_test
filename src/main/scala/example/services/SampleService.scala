package example.services

import akka.actor.ActorRef
import akka.util.Timeout
import example.actors.CalculatingCollector.Calculate
import spray.httpx.SprayJsonSupport
import spray.routing.{Directives, Route}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class SampleService(actor: ActorRef)(implicit exContext: ExecutionContext) extends Directives with SprayJsonSupport {

  override val pathEnd = pathEndOrSingleSlash
  implicit val timeout: Timeout = 20.seconds

  def route: Route =
    pathEnd(get(complete("Hello from analytics service"))) ~
    pathPrefix("test") {
      get(complete {
        actor ! Calculate
        "OK"
      })
      }
}
