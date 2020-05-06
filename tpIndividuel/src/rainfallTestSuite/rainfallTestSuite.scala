package rainfallTestSuite

import rainfall2P.rainfall2P
import rainfallOS.rainfallOS
import rainfallS.rainfallS

object rainfallTestSuite {
  def main(args: Array[String]): Unit = {
    rainfallS.main(Array.apply())
    rainfallOS.main(Array.apply())
    rainfall2P.main(Array.apply())
  }
}
