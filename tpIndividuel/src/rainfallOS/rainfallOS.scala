package rainfallOS

import rainfall.rainfall

object rainfallOS extends rainfall{

  def rainfall(rainfalls: List[Int]): Option[Double] = {
    val tmp = rainfalls.takeWhile(_ != -999).filter(_ >= 0)
    val n = tmp.size
    n match {
      case x if x != 0 => Option(tmp.reduceOption(_ + _).head.toDouble / n)
      case _ => None
    }
  }
}