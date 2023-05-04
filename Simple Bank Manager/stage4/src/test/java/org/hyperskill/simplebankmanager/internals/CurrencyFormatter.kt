package org.hyperskill.simplebankmanager.internals

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class CurrencyFormatter {
    companion object {
        fun formatCurrency(balance: Double): String {
            return NumberFormat.getCurrencyInstance(Locale("en", "US")).format(balance)
        }

        fun formatCurrencySpinner(currency: String, amount: Double): String {
            val currency = when (currency.uppercase()) {
                "EUR" -> "EU"
                "USD" -> "US"
                "GBP" -> "GB"
                else -> ""
            }
            if (currency == "EU") {  // problem with returning symbol "¤" ->  because the Euro is used in multiple countries with different currency symbols, the NumberFormat class may not be able to find a specific currency symbol for the Euro currency when using the Locale("en", "EU") parameter.
                val euro = Currency.getInstance("EUR")
                val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", currency))
                format.currency = euro
                return format.format(amount)
            }
            return NumberFormat.getCurrencyInstance(Locale("en", currency)).format(amount)
        }

    }
}
