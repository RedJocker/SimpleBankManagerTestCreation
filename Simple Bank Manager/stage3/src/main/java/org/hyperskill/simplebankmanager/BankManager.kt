package org.hyperskill.simplebankmanager

interface BankManager : LoginFragment.LoginValidator, BalanceSupplier

interface BalanceSupplier {
    fun currentBalance(): Double
}