package rainfall2P

import rainfall.rainfall

object rainfall2P extends rainfall{

  import scala.collection.parallel.CollectionConverters._

  def rainfall(rainfalls: List[Int]): Option[Double] = {
    val tmp = rainfalls.takeWhile(_ != -999).filter(_ >= 0)
    val n = tmp.size
    n match {
      case x if x != 0 => Option(tmp.par.reduceOption(_ + _).head.toDouble / n)
      case _ => None
    }
  }
}