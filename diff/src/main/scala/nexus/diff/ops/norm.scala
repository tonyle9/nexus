package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus._
import nexus.syntax._

/**
 * Absolute value.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Abs extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Abs"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.abs(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.sgn(x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.abs(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.sgn(x)
}


object L1Norm extends PolyOp1 {

  implicit def l1NormF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], R] =
    new F[T[A], R] {
      def name = "L1Norm"
      def tag = Tag.real[R]
      def forward(x: T[A]) = T.sum(T.abs(x))
      def backward(dy: R, y: R, x: T[A]) = T.sgn(x) :* dy
    }
}

object L2Norm extends PolyOp1 {

  implicit def l2NormF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], R] =
    new F[T[A], R] {
      def name = "L2Norm"
      def tag = Tag.real[R]
      def forward(x: T[A]) = T.R.sqrt(T.dot(x, x))
      def backward(dy: R, y: R, x: T[A]) = x :* T.R.div(dy, y)
    }
}

object LpNorm extends ParameterizedPolyOp1 {

  implicit def lpNormF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = (p: Double) =>
      new F[T[A], R] {
        def name = s"LpNorm[$p]"
        def tag = Tag.real[R]
        def forward(x: T[A]) = ???
        def backward(dy: R, y: R, x: T[A]) = ???
      }

}
