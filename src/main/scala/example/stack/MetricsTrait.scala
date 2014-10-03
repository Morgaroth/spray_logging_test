package example.stack

import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

import com.codahale.metrics.CsvReporter
import com.codahale.metrics.ganglia.GangliaReporter
import info.ganglia.gmetric4j.gmetric.GMetric
import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode


object Metrics {
  val metricRegistry = new com.codahale.metrics.MetricRegistry()

  //  val gangliaServer = new GMetric("192.168.56.110", 8649, UDPAddressingMode.UNICAST, 1)
  //  val reporter = GangliaReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build(gangliaServer)
  //  reporter.start(15, TimeUnit.SECONDS)
  def printAndReturn[T](s: T): T = {
    println(s.toString)
    s
  }

  private val file: File = new File("/home/client_1/")
  println(s"used folder ${file.getAbsolutePath}")
  val csvReporter = CsvReporter.forRegistry(metricRegistry)
    .formatFor(Locale.forLanguageTag("PL"))
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build(file)
  csvReporter.start(10, TimeUnit.SECONDS)
}

trait Instrumented extends nl.grons.metrics.scala.InstrumentedBuilder {
  val metricRegistry = Metrics.metricRegistry

}
