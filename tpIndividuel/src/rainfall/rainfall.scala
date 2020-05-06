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

