package money

object Currency extends Enumeration {
  type Currency = Value
  val Euro:   money.Currency.Value  = Value("EUR")
  val Dollar: money.Currency.Value  = Value("USD")
  val Yen:    money.Currency.Value  = Value("JPY")

  private val locale: money.Currency.Value = Euro

  def exchangeRate(currency: Currency): Map[Currency, Double] = {
    currency match {
      case Euro   => Map(Euro   -> 1,          Dollar -> 1.15850,    Yen    -> 131.676)
      case Dollar => Map(Euro   -> 0.863187,   Dollar -> 1,          Yen    -> 113.648)
      case Yen    => Map(Euro   -> 0.00759441, Dollar -> 0.00879910, Yen    -> 1      )
      case _      => throw new IllegalArgumentException(s"Unkown currency $currency.")
    }
  }

  def double2Locale(i: Double): Account = new Account(i, locale)

  def main(args: Array[String]): Unit = {
    val dollarExchangeRate = Map(Euro   -> 0.863187,
                                 Dollar -> 1,
                                 Yen    -> 113.648)
    println(s"Euro :                     $Euro")
    println(s"DollarExchangeRate :       $dollarExchangeRate")
    println(s"DollarExchangeRate(Euro) : ${dollarExchangeRate(Euro)}")
  }

}


