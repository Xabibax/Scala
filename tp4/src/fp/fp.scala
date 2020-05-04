package object fp {
  import common._

  implicit def toInt(v: Val): Int = v match {
    case IntV(i) => i;
    case _ => throw new Exception(s"Cannot transform BoolV in Int : $v")
  }
  implicit def intToVal(i: Int): Val = IntV(i)
  implicit def toBool(v: Val): Boolean = v match {
    case BoolV(b) => b
    case _ => throw new Exception(s"Cannot transform BoolV in Int : $v")
  }
  implicit def boolToVal(b: Boolean): Val = BoolV(b)

  def eval(exp: Exp): Val = {
    check(exp) match {
      case I => exp match {
        case IntLit(v) => IntV(v)
        case AExp1(op, e1) => op.toVal(eval(e1))
        case AExp2(op, e1, e2) => op.toVal(eval(e1), eval(e2))
        case _ => throw new Exception(s"Cannot use Logical Expression on Integers : ${toInfix(exp)}")
      }
      case B => exp match {
        case BoolLit(v) => BoolV(v)
        case LExp1(op, e1) => op.toVal(eval(e1))
        case LExp2(op, e1, e2) => op match {
          case EQ => (check(e1),check(e2)) match {
            case (B,B) => op.toVal(eval(e1), eval(e2));
            case (I,I) => op.toVal(eval(e1), eval(e2))
            case _ => BoolV(false)
          }
          case _ => op.toVal(eval(e1), eval(e2))
        }
        case _ => throw new Exception(s"Cannot use Arithmetical Expression on Booleans : ${toInfix(exp)}")
      }
      case _ => throw new Exception(s"Can't eval unknown expression : ${toInfix(exp)}")
    }
  }

  def toInfix(exp: Exp): String = exp match {
    case IntLit(v) => v.toString
    case AExp1(op, e1) => op.toInfix(toInfix(e1))
    case AExp2(op, e1, e2) => op.toInfix(toInfix(e1), toInfix(e2))
    case BoolLit(v) => v.toString
    case LExp1(op, e1) => op.toInfix(toInfix(e1))
    case LExp2(op, e1, e2) => op.toInfix(toInfix(e1), toInfix(e2))
    case _ => throw new Exception(s"Can't infix unknown expression : ${toInfix(exp)}")
  }

  def check(exp: Exp): Type = exp match {
    case IntLit(_) => I
    case AExp1(op, e1) => check(e1) match { case I => I; case _ => throw new Exception(s"""Arithmetical expression "$op" required one Integer but found : ${toInfix(e1)} """) }
    case AExp2(op, e1, e2) => (check(e1), check(e2)) match {
      case (I,I) => I;
      case _ => throw new Exception(s"""Arithmetical expression "$op" required two Integer but found : ${toInfix(e1)}, ${toInfix(e2)}""")
    }
    case BoolLit(_) => B
    case LExp1(op, e1) => check(e1) match { case B => B; case _ => throw new Exception(s"""Arithmetical expression "$op" required one Boolean but found : ${toInfix(e1)} """) }
    case LExp2(op, e1, e2) => op match {
      case EQ => B
      case _ => (check(e1),check(e2)) match {
        case (B,B) => B
        case (I,I) => B
        case _ =>throw new Exception(s"""Logical expression "$op" required two Booleans but found : ${toInfix(e1)}, ${toInfix(e2)}""")
      }
    }
    case _ => throw new Exception(s"""Unknown expression : ${toInfix(exp)}""")
  }

  sealed trait Exp
  case class IntLit(v: Int) extends Exp
  case class AExp1(op: Op1[Int], e1: Exp) extends Exp
  case class AExp2(op: Op2[Int], e1: Exp, e2: Exp) extends Exp
  case class BoolLit(v: Boolean) extends Exp
  case class LExp1(op: Op1[Boolean], e1: Exp) extends Exp
  case class LExp2(op: Op2[Boolean], e1: Exp, e2: Exp) extends Exp
}


