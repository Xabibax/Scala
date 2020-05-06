package rainfallS

object rainfallS {

  import scala.collection.parallel.CollectionConverters._

  def rainfall(rainfalls: List[Int]): Option[Double] = {
    val tmp = rainfalls.par.takeWhile(_ != -999).filter(_ >= 0)
    val n = tmp.size
    n match {
      case x if x != 0 => Option(tmp.par.reduceOption(_ + _).head.toDouble / n)
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