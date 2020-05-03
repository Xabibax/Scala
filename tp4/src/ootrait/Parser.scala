package ootrait
import common._

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

class Parser extends RegexParsers {




  val number: Regex = "0|[1-9]+".r
  val bool  : Regex = "true|false".r
  def exp: Parser[ExpEIC] =  "(" ~> "-" ~> exp <~ ")" ^^ (exp => AExp1(MINUS, exp)) |
                            "(" ~> "+" ~> exp ~ exp <~ ")" ^^ { case exp1 ~ exp2 => AExp2(PLUS, exp1, exp2) } |
                            "(" ~> "*" ~> exp ~ exp <~ ")" ^^ { case exp1 ~ exp2 => AExp2(TIMES, exp1, exp2) } |
                            "(" ~> "!" ~> exp <~ ")" ^^ (exp1 => LExp1(NOT, exp1)) |
                            "(" ~> "==" ~> exp ~ exp <~ ")" ^^ { case exp1 ~ exp2 => LExp2(EQ, exp1, exp2) } |
                            "(" ~> "&&" ~> exp ~ exp <~ ")" ^^ { case exp1 ~ exp2 => LExp2(AND, exp1, exp2) } |
                            "(" ~> "||" ~> exp ~ exp <~ ")" ^^ { case exp1 ~ exp2 => LExp2(OR, exp1, exp2) } |
                            number ^^ (n => IntLit(n.toInt)) |
                            bool ^^ (n => BoolLit(n.toBoolean))
}

