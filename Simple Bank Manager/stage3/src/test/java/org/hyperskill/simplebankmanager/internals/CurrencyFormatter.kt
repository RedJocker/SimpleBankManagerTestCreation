package org.hyperskill.simplebankmanager.internals

import java.text.NumberFormat
import java.util.Locale

class CurrencyFormatter {
    companion object {
        fun formatCurrency(balance: Double): String {
            return NumberFormat.getCurrencyInstance(Locale("en", "US")).format(balance)
        }
    }
}
