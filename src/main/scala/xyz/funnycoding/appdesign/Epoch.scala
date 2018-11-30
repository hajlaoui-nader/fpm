package xyz.funnycoding.appdesign

import scala.concurrent.duration._

final case class Epoch(millis: Long) extends AnyVal {
  def +(d: FiniteDuration): Epoch = Epoch(d.toMillis + millis)
  def -(e: Epoch): FiniteDuration = (millis - e.millis).millis
}