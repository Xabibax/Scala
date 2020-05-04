package rational

class Rational( n: Int,  d: Int) {
  def this(n: Int) {
    this(n, 1)
  }

  def getN = n

  def getD = d

  override def toString = s"$n/$d"

  def add(that: Rational) = this + that
  def +(that: Rational) = new Rational(n * that.getD + that.getN * d, d * that.getD)
  def *(that: Rational) = new Rational(n * that.getN, d * that.getD)
}
object Rational {
  val r1 = new Rational(1).add(new Rational(1, 2))
  val r2 = Rational(1, 2) + Rational(1)

  def apply(n: Int) = new Rational(n)
  def apply(n: Int, d: Int) = new Rational(n, d)

  def main(args: Array[String]): Unit = {
    System.out.println(r1)
    System.out.println(r2)

    System.out.println(r1.add(r2))
    System.out.println(r1 + r2)

    System.out.println(r1 * r2)
  }
}