package money

import money._
import Currency._
import money.Account.Factor

class Account (val amount: Double, val currency: Currency) {

  override def toString = s"$amount $currency"

  def +(that: Account) = Account(amount + that.amount * exchangeRate(that.currency)(currency), currency)
  def *(that: Factor)  = that * amount
}

object Account {
  def apply(i: Double, Euro: money.Currency.Value) = new Account(i, Euro)
  implicit def convert(that: Double): Account = double2Locale(that)

  implicit class Factor(var factor: Double) {
    override def toString = s"$factor"

    def *(account: Account) = Account(factor * account.amount, account.currency)
  }
  def double2Factor(factor: Double): Factor = new Factor(factor)
}