package oo

import common.{Op2, Op1}

sealed trait Exp {
  def eval: Int
  def toInfix: String
}

case class IntLit(v: Int) extends Exp {
  def eval: Int = v
  def toInfix: String = v.toString
}

case class AExp1(op: Op1[Int], e1: Exp) extends Exp {
  def eval: Int = op.toVal(e1.eval)
  def toInfix: String = op.toInfix(e1.toInfix)
}

case class AExp2(op: Op2[Int], e1: Exp, e2: Exp) extends Exp {
  def eval: Int = op.toVal(e1.eval, e2.eval)
  def toInfix: String = op.toInfix(e1.toInfix, e2.toInfix)
}

