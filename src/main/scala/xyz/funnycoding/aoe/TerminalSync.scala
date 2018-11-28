package xyz.funnycoding.aoe
import scala.concurrent.Future

trait Terminal[C[_]] {
  def read(): C[String]
  def write(t: String): C[Unit]
}

object TerminalSync extends Terminal[Now]{
  def read(): String = ???
  def write(t: String): Unit = ???
}

object TerminalAsync extends Terminal[Future]{
  def read(): Future[String] = ???
  def write(t: String): Future[Unit] = ???
}
