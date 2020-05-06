package rainfall

import rainfall2P.rainfall2P
import rainfallOS.rainfallOS
import rainfallS.rainfallS

trait rainfall {
  def rainfall(rainfalls: List[Int]): Option[Double]

  def test(rainfalls: List[Int]): Unit = {
    println("====")
    println(rainfalls)
    println(if (rainfall(rainfalls).isDefined) rainfall(rainfalls) else "No Data")
  }

  def main(args: Array[String]): Unit = {
    var start = System.currentTimeMillis()
    println(s"${System.lineSeparator()}Running ${this.getClass} tests :")
    test(Nil)
    test(List(0))
    test(List(- 999 ))
    test(List(- 1 , - 2 ))
    test(List(1, 2))
    test(List(1, - 3 , 2))
    test(List(1, 2, - 3 ))
    test(List(1, - 999 , 2, - 3 ))
//    var l: List[Int] = Nil
//    for (w <- 0 to 1000990990) {
//      //l = (new scala.util.Random).nextInt(999) :: l
//      w::l
//    }
//    test(l)
    println(s"${System.lineSeparator()}Execution time for ${this.getClass} tests :")
    println(s"${System.currentTimeMillis() - start} millisecondes.")

  }
}

