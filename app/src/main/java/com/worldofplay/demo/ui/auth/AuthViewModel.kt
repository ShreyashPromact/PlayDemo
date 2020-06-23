package com.worldofplay.demo.ui.auth

import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModel
import com.worldofplay.demo.data.repositories.UserRepository
import java.util.regex.Pattern

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var isChange: Boolean = false

    var authListener: AuthListener? = null
    var themeListener: ThemeListener? = null

    fun onThemeChange(view: View) {
        themeListener?.changeTheme(isChange)
    }

    fun onLoginClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Email or Password are empty")
            return
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authListener?.onFailure("Enter valid email")
            return
        } else if(password!!.length < 8  || password!!.length > 16 || !Pattern.matches("^(?=.{10,}\$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*\$", password)) {
            authListener?.onFailure("Password should be of 8-16 characters length including at least 1 uppercase, 1 lowercase, 1 number and 1 special character.")
            return
        } else if (!email.equals("test@worldofplay.in") && !password.equals("Worldofplay@2020")) {
            // IMPORTANT ::: this can be done from mock API can right now handling at client side
            authListener?.onFailure("Email & Password are not authorised!")
            return
        }
        // We will get Success here
        val loginResponse = UserRepository().userLogin(email!!, password!!)
        authListener?.onSuccess(loginResponse)
    }
}