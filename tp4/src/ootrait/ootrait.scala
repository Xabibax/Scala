package object ootrait {
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

  sealed trait Exp
  trait ExpE extends Exp { def eval: Val }
  trait ExpI extends Exp { def toInfix: String }
  trait ExpC extends Exp { def check: Type }
  trait ExpEIC extends ExpE with ExpI with ExpC

  trait IntLit extends ExpEIC { val v: Int }
  trait IntLitE extends IntLit with ExpE { def eval = IntV(v) }
  trait IntLitI extends IntLit with ExpI { def toInfix = v.toString }
  trait IntLitC extends IntLit with ExpC { def check = I }
  trait IntLitEIC extends IntLitE with IntLitI with IntLitC with ExpEIC
  implicit def IntLit(i: Int): IntLit = new IntLitEIC {
    override val v = i
  }

  trait AExp1 extends Exp { val op: Op1[Int] }
  trait AExp1E extends AExp1 with ExpE {
    val e1: ExpEIC
    def eval = e1.check match {
      case I => op.toVal(e1.eval)
      case _ => throw new Exception(s"""Arithmetical expression "$op" required one Integer but found : ${e1.toInfix} """)
    }
  }
  trait AExp1I extends AExp1 with ExpI {
    val e1: ExpEIC
    def toInfix = op.toInfix(e1.toInfix)
  }
  trait AExp1C extends AExp1 with ExpC {
    val e1: ExpEIC
    def check = e1.check match {
      case I => I
      case _ => throw new Exception(s"Cannot use Logical Expression on Integers : ${e1.toInfix}")
    }
  }
  trait AExp1EIC extends AExp1E with AExp1I with AExp1C with ExpEIC
  implicit def AExp1(ope1: Op1[Int], exp1: ExpEIC): AExp1EIC = new AExp1EIC {
    override val e1 : ExpEIC = exp1
    override val op : Op1[Int]  = ope1
  }

  trait AExp2 extends Exp { val op: Op2[Int] }
  trait AExp2E extends AExp2 with ExpE {
    val e1, e2: ExpEIC
    def eval = (e1.check, e2.check) match {
      case (I,I) => op.toVal(e1.eval, e2.eval)
      case _ => throw new Exception(s"""Arithmetical expression "$op" required two Integer but found : ${e1.toInfix}, ${e2.toInfix}""")
    }
  }
  trait AExp2I extends AExp2 with ExpI {
    val e1, e2: ExpEIC
    def toInfix = op.toInfix(e1.toInfix, e2.toInfix)
  }
  trait AExp2C extends AExp2 with ExpC {
    val e1, e2: ExpEIC
    def check = (e1.check, e2.check) match {
      case (I,I) => I;
      case _ => throw new Exception(s"Cannot use Arithmetical Expression on Booleans : ${e1.toInfix}, ${e2.toInfix}")
    }
  }
  trait AExp2EIC extends AExp2E with AExp2I with AExp2C with ExpEIC
  implicit def AExp2(ope2: Op2[Int], exp1: ExpEIC, exp2: ExpEIC): AExp2EIC = new AExp2EIC {
    override val e1 : ExpEIC = exp1
    override val e2 : ExpEIC = exp2
    override val op : Op2[Int] = ope2
  }

  trait BoolLit extends ExpEIC { val v: Boolean }
  trait BoolLitE extends BoolLit with ExpE { def eval = BoolV(v) }
  trait BoolLitI extends BoolLit with ExpI { def toInfix = v.toString }
  trait BoolLitC extends BoolLit with ExpI { def check = B }
  trait BoolLitEIC extends BoolLitE with BoolLitI with BoolLitC with ExpEIC
  implicit def BoolLit(b: Boolean): BoolLit = new BoolLitEIC {
    override val v = b
  }

  trait LExp1 extends Exp { val op: Op1[Boolean] }
  trait LExp1E extends LExp1 with ExpE {
    val e1: ExpEIC
    def eval = e1.check match {
      case B => op.toVal(e1.eval)
      case _ => throw new Exception(s"""Logical expression "$op" required one Boolean but found : ${e1.toInfix}""")
    }
  }
  trait LExp1I extends LExp1 with ExpI {
    val e1: ExpEIC
    def toInfix = op.toInfix(e1.toInfix)
  }
  trait LExp1C extends LExp1 with ExpC {
    val e1: ExpEIC
    def check = e1.check match {
      case B => B
      case _ => throw new Exception(s"Cannot use Logical Expression on Integers : ${e1.toInfix}")
    }
  }
  trait LExp1EIC extends LExp1E with LExp1I with LExp1C with ExpEIC
  implicit def LExp1(ope: Op1[Boolean], exp1: ExpEIC): LExp1EIC = new LExp1EIC {
    override val e1 : ExpEIC = exp1
    override val op : Op1[Boolean]  = ope
  }

  trait LExp2 extends Exp { val op: Op2[Boolean] }
  trait LExp2E extends LExp2 with ExpE {
    val e1, e2: ExpEIC
    def eval = op match {
      case EQ => (e1.check, e2.check) match {
        case (B,B) => op.toVal(e1.eval, e2.eval)
        case (I,I) => op.toVal(e1.eval, e2.eval)
        case _ => BoolV(false)
      }
      case _ => (e1.check, e2.check) match {
        case (B,B) => op.toVal(e1.eval, e2.eval)
        case _ => throw new Exception(s"""Logical expression "$op" required two Booleans but found : ${e1.toInfix}, ${e2.toInfix}""")
      }
    }
  }
  trait LExp2I extends LExp2 with ExpI {
    val e1, e2: ExpEIC
    def toInfix = op.toInfix(e1.toInfix, e2.toInfix)
  }
  trait LExp2C extends LExp2 with ExpC {
    val e1, e2: ExpEIC
    def check = op match {
      case EQ => B
      case _ => (e1.check, e2.check) match {
        case (B,B) => B;
        case _ => throw new Exception(s"Cannot use Logical Expression on Integers : ${e1.toInfix}, ${e2.toInfix}")
      }
    }
  }
  trait LExp2EIC extends LExp2E with LExp2I with LExp2C with ExpEIC
  implicit def LExp2(ope: Op2[Boolean], exp1: ExpEIC, exp2: ExpEIC): LExp2EIC = new LExp2EIC {
    override val e1 : ExpEIC = exp1
    override val e2 : ExpEIC = exp2
    override val op : Op2[Boolean] = ope
  }
}
