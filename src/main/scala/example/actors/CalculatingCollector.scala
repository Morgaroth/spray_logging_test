package example.actors

import akka.actor.{ActorRef, Props, Actor}
import com.codahale.metrics.Timer
import com.codahale.metrics.Timer.Context

import example.actors.CalculatingCollector._
import example.stack.Instrumented

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
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


class CalculatingActor extends Actor with Instrumented {

  val timer: Timer = metricRegistry.timer("sorting 100000 randoms")

  override def receive: Receive = {
    case Calculate =>
      val context = timer.time()
      println(s"actor $self is calculating")
      sender() ! generateRandomIntList(100000L).sorted.reduce(_ + _)
      context.stop()
  }

}


class CalculatingCollector extends Actor with Instrumented {
  var asking: ActorRef = _
  var list = ListBuffer[BigInt]()
  var size = 0
  val timer = metricRegistry.timer("main JOB")
  var timeConvext: Option[Context] = None

  override def receive: Receive = {
    case result: BigInt =>
      println(s"actor $self received result $result")
      list += result
      size -= 1
      if (size == 0) {
        asking ! list.reduce(_ + _)
        timeConvext.map(_.stop()).getOrElse(println("timer is NONE!"))
      }

    case Calculate =>
      timeConvext = Some(timer.time())
      asking = sender()
      size = 1000
      for (i <- 0 until size) {
        context.actorOf(Props[CalculatingActor]) ! Calculate
      }
  }
}
