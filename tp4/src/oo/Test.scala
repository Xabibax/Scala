package oo

object Test extends App {
  var count = 0
  var max = 0
  val parser = new Parser
  def test(program: String, expected: Option[Int]): Unit = {
    println(s"==== $program")
    max += 1
    var result: Option[Int] = None
    val parse = parser.parseAll(parser.exp, program)
    if (parse.successful) {
      val ast = parse.get
      try {
        result = Some(ast.eval)
        println(s"AST: $ast")
        println(s"infix version: ${ast.toInfix}")
        println(s"=> $result")
      } catch {
        case ex: Exception => ex.printStackTrace()
      }
    }
    if (result == expected) {
      count += 1
      println("SUCCESS")
    } else
      println("FAILURE")
  }
  def report(): Unit = println(s"\nSCORE: $count/$max")
  test("garbage", None)
  test("42", Some(42))
  test("(+ 3 4)", Some(7))
  test( "(- 3)", Some(-3))
  test( "(* 3 4)", Some(12))
  test( "(* 3 (+ 2 (- 4)))", Some(-6))
/*
  test("true", Some(true))
  test("(&& false true)", Some(false))
  test( "(! false)", Some(true))
  test( "(|| true false)", Some(true))
  test( "(|| false (&& true (! true)))", Some(false))
  test("(== true 1)" , Some(false))
  test("(== true false)" , Some(false))
  test("(== (! true) (&& false true))" , Some(true))
  test("(+ 1 true)", None)
  test("(&& 1 true)", None)
*/
  report()
}