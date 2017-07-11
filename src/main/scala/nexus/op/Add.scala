package nexus.op

import nexus._

/**
 * Addition of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends GenOp2[AddF]

@impMsg("Cannot apply Add to ${X1} and ${X2}.")
trait AddF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Add"
}

object AddF {

  implicit def AddImpl[T[D, _ <: $$], D, A <: $$](implicit env: Env[T, D]): AddF[T[D, A], T[D, A], T[D, A]] =
    new AddF[T[D, A], T[D, A], T[D, A]] {
      def forward(x1: T[D, A], x2: T[D, A]) = x1 + x2
      def backward1(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy
      def backward2(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy
    }
}
