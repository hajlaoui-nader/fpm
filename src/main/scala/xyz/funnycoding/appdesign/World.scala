package xyz.funnycoding.appdesign

import scalaz.Scalaz._
import scalaz.{Monad, NonEmptyList}

import scala.concurrent.duration.DurationLong

final case class WorldView(backlog: Int,
                           agents: Int,
                           managed: NonEmptyList[MachineNode],
                           alive: Map[MachineNode, Epoch],
                           pending: Map[MachineNode, Epoch],
                           time: Epoch
                          )

trait DynAgents[F[_]] {
  def initial: F[WorldView]
  def update(old: WorldView): F[WorldView]
  def act(world: WorldView): F[WorldView]
}

final class DynAgentsModule[F[_]: Monad](D: Drone[F], M: Machines[F])  extends DynAgents[F] {

  override def initial: F[WorldView] = for {
    db <- D.getBacklog
    da <- D.getAgents
    mm <- M.getManaged
    ma <- M.getAlive
    mt <- M.getTime
  } yield WorldView(db, da, mm, ma, Map.empty, mt)

  override def update(old: WorldView): F[WorldView] = for {
    snap <- initial
    changed = symdiff(old.alive.keySet, snap.alive.keySet)
    pending = (old.pending -- changed).filterNot {
      case (_, started) => (snap.time - started) >= 10.minutes
    }
    update = snap.copy(pending = pending)
  } yield update

  override def act(world: WorldView): F[WorldView] = ???


}