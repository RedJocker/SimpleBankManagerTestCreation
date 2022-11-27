package org.hyperskill.simplebankmanager

interface LoginValidator {
    fun isValidLogin(username: String, password: String): Boolean
}