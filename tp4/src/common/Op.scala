package common

sealed trait Op1[T] {
  def toVal: T => T
  def toInfix: String => String
}
case object MINUS extends Op1[Int] {
  def toVal: Int => Int = - _
  def toInfix: String => String = "-" + _
}

sealed trait Op2[T] {
  def toVal: (T, T) => T
  def toInfix: (String, String) => String
}
case object PLUS extends Op2[Int] {
  def toVal: (Int, Int) => Int = _ + _
  def toInfix: (String, String) => String = (e1, e2) => s"($e1 + $e2)"
}
case object TIMES extends Op2[Int] {
  def toVal: (Int, Int) => Int = _ * _
  def toInfix: (String, String) => String = (e1, e2) => s"($e1 * $e2)"
}
