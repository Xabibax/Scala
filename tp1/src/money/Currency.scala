package money

object Currency extends Enumeration {
  type Currency = Value
  val Euro: money.Currency.Value = Value("EUR")
  val Dollar: money.Currency.Value = Value("USD")
  val Yen: money.Currency.Value = Value("JPY")

  def main(args: Array[String]): Unit = {
    val dollarExchangeRate = Map(Euro -> 0.863187, Dollar -> 1, Yen -> 113.648)
    println(s"Euro :                     $Euro")
    println(s"DollarExchangeRate :       $dollarExchangeRate")
    println(s"DollarExchangeRate(Euro) : ${dollarExchangeRate(Euro)}")
  }

}


