package org.hyperskill.simplebankmanager

interface BankManager : LoginValidator,
    BalanceSupplier,
    BalanceSetter,
    AccountNumberValidator

interface LoginValidator {
    fun isValidLogin(username: String, password: String): Boolean
}

interface BalanceSupplier {
    fun currentBalance(): Double
}

interface BalanceSetter {
    fun hasFunds(amount: Double): Boolean
    fun subtractBalance(amount: Double)
}

interface AccountNumberValidator {
    fun isValidAccount(account: String): Boolean
}