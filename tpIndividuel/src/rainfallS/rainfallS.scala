package rainfallS

object rainfallS {

  import scala.util.control.Breaks._
  def rainfall(rainfalls: List[Int]): Option[Double] = {
    var sum = 0
    var n = 0
    for (rainfall <- rainfalls)
      breakable {
        rainfall match {
          case -999 => break()
          case x if x >= 0 =>
            n += 1
            sum += rainfall
          case _ =>
        }
      }
    n match {
      case x if x != 0 => Option(sum.toDouble / n)
      case _ => None
    }
  }

  def test(rainfalls: List[Int]): Unit = {
    println("====")
    println(rainfalls)
    println(if (rainfall(rainfalls).isDefined) rainfall(rainfalls) else "No Data")
  }

  def main(args: Array[String]): Unit = {
    test(Nil)
    test(List(0))
    test(List(- 999 ))
    test(List(- 1 , - 2 ))
    test(List(1, 2))
    test(List(1, - 3 , 2))
    test(List(1, 2, - 3 ))
    test(List(1, - 999 , 2, - 3 ))
  }
}