package rational

import org.junit.jupiter.api.Assertions._

object RationalScalaTest {
  val r1 = new Rational(1).add(new Rational(1, 2))
  val r2 = new Rational(1, 2) + new Rational(1)

  def main(args: Array[String]) = {
    System.out.println(r1)
    System.out.println(r2)

    System.out.println(r1.add(r2))
    System.out.println(r1 + r2)

    System.out.println(r1 * r2)
  }
}