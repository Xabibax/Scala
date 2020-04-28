package tp3

class tp3{

}
object tp3 {
  def decorate(left: String, s: String, right: String) = left + s + right
  def decorateQ = (s: String) => decorate("\"", s, "\"")
  def decorateC: String => (String => String => String) = s => ( l => r => decorate(l, s, r))


  def fact(n: Int): Int = if (n == 0) 1 else n * fact(n-1)

  def fact2(n: BigInt): BigInt = {
    def _fact2(n: BigInt, res: BigInt): BigInt = if (n > 1) _fact2(n - 1, n * res) else res
    _fact2(n, 1)
  }

  def main(args: Array[String]): Unit = {
    println("decorateQ(\"Foo\")",decorateQ("Foo"))
    println("decorateC(\"Foo\")(\"\\\")(\"/\")",decorateC("Foo")("\\")("/"))

    println("fact(9) = ", fact(9))
    println("fact2(9) = ", fact2(9))
    println("fact2(0) = ", fact2(1))
    println("fact2(0) = ", fact2(0))
    println("fact2(0) = ", fact2(-1))
    println("fact2(9) = ", fact2(100))
  }
}