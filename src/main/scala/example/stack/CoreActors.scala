package example.stack

import akka.actor.Props
import example.actors.CalculatingCollector

trait CoreActors {
  this: Core =>

  // actors
  val calc = system.actorOf(Props[CalculatingCollector], "collector")
}
