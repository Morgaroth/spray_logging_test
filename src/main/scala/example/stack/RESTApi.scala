package example.stack

import akka.actor.Props
import example.services.SampleService
import spray.routing.{Directives, Route, RouteConcatenation}

trait RESTApi extends RouteConcatenation with Directives {
  this: CoreActors with Core =>
  private implicit val _ = system.dispatcher

  // TODO routes to fill and concatenate below
  val calcRoute = new SampleService(calc).route


  val routes: Route =
    pathPrefix("") {
      pathEndOrSingleSlash {
        get(complete("Hello from Your Spray App!"))
      }
    } ~
    pathPrefix("test") {
      calcRoute
    }

  val rootService = system.actorOf(Props(new RoutedHttpService(routes)))
}
