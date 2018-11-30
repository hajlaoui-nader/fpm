package xyz.funnycoding

package object appdesign {
  def symdiff[T](a: Set[T], b: Set[T]): Set[T] =
    (a union b) -- (a intersect b)
}
