package com.worldofplay.demo.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.worldofplay.demo.R
import com.worldofplay.demo.databinding.ActivityLoginBinding
import com.worldofplay.demo.util.hide
import com.worldofplay.demo.util.show
import com.worldofplay.demo.util.toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), AuthListener, ThemeListener {

    var dark = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (dark) R.style.AppDarkTheme else R.style.AppNormalTheme)
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =  DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
        viewModel.themeListener = this
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }

    override fun changeTheme(darkTheme: Boolean) {
        dark = darkTheme
        recreate()
    }
}