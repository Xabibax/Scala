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

case object NOT extends Op1[Boolean] {
  def toVal: Boolean => Boolean = ! _
  def toInfix: String => String = e1 => s"(!$e1)"
}

case object EQ extends Op2[Boolean] {
  def toVal: (Boolean, Boolean) => Boolean = _ == _
  def toInfix: (String, String) => String = (e1, e2) => s"($e1 == $e2)"
}

case object AND extends Op2[Boolean] {
  def toVal: (Boolean, Boolean) => Boolean = _ && _
  def toInfix: (String, String) => String = (e1, e2) => s"($e1 && $e2)"
}

case object OR extends Op2[Boolean] {
  def toVal: (Boolean, Boolean) => Boolean = _ || _
  def toInfix: (String, String) => String = (e1, e2) => s"($e1 || $e2)"
}
