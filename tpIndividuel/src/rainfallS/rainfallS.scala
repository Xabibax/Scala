package rainfallS

import rainfall.rainfall

object rainfallS extends rainfall{

  import scala.util.control.Breaks._
  def rainfall(rainfalls: List[Int]): Option[Double] = {
    var sum = 0
    var n = 0
    breakable {
      for (rainfall <- rainfalls)
        rainfall match {
          case -999 => break
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
}