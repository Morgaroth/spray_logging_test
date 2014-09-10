package example.actors

import akka.actor.{ActorRef, Props, Actor}

import example.actors.CalculatingCollector._

import scala.collection.mutable.ListBuffer
import scala.util.Random

object CalculatingCollector {

  case object Message

  case object Calculate

  val random = Random


  def generateRandomIntList(count: Long) = {
    val buf = ListBuffer[BigInt]()
    1L to count foreach {
      x => buf += random.nextInt(500)
    }
    val res: List[BigInt] = buf.toList
    assert(res.size == count, "DUPA")
    res
  }
}


class CalculatingActor extends Actor {

  override def receive: Receive = {
    case Calculate =>
      println(s"actor $self is calculating")
      sender() ! generateRandomIntList(100000L).sorted.reduce(_ + _)
  }
}


class CalculatingCollector extends Actor {
  var asking: ActorRef = _
  var list = ListBuffer[BigInt]()
  var size = 0

  override def receive: Receive = {
    case result: BigInt =>
      println(s"actor $self received result $result")
      list += result
      size -= 1
      if (size == 0) {
        asking ! list.reduce(_ + _)
      }

    case Calculate =>
      asking = sender()
      size = 1000
      for (i <- 0 until size) {
        context.actorOf(Props[CalculatingActor]) ! Calculate
      }
  }
}
