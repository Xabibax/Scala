package common

sealed trait Val
case class IntV(i: Int) extends Val {
  def +(that: IntV) = IntV(that.i + i)
  def -() = IntV(-i)
  def unary_-() = IntV(-i)
  def *(that: IntV) = IntV(that.i * i)
}
case class BoolV(b: Boolean) extends Val {
  def !() = BoolV(!b)
  def ==(that: BoolV) = BoolV(that.b == b)
  def &&(that: BoolV) = BoolV(that.b && b)
  def ||(that: BoolV) = BoolV(that.b || b)
}
