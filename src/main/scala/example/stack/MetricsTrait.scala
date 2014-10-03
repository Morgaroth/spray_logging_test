package example.stack

import info.ganglia.gmetric4j.gmetric.GMetric
import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode


object Metrics {

  val metricRegistry = new com.codahale.metrics.MetricRegistry()
  val gangliaReporter = new GMetric("192.168.56.110", 8649, UDPAddressingMode.UNICAST, 1)
}

trait Instrumented extends nl.grons.metrics.scala.InstrumentedBuilder {

  val metricRegistry = Metrics.metricRegistry

}
