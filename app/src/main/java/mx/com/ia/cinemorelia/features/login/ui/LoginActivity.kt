package mx.com.ia.cinemorelia.features.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.databinding.ActivityLoginBinding
import mx.com.ia.cinemorelia.features.login.models.LoginBodyModel
import mx.com.ia.cinemorelia.features.login.viewmodel.LoginViewModel
import mx.com.ia.cinemorelia.ui.CinemoreliaActivity
import mx.com.ia.cinemorelia.ui.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity: CinemoreliaActivity() {
    private lateinit var bind: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        listeners()
        viewBinding()
    }

    private fun viewBinding() {
        viewModel.login().observe(this, ::observers)
    }

    private fun observers(states: LoginViewModel.StateActions) {
        when (states) {
            is LoginViewModel.StateActions.Loading -> {
                bind.btLogin.isEnabled = false
                bind.pbLogin.visibility = View.VISIBLE
            }
            is LoginViewModel.StateActions.LoginResult -> {
                bind.btLogin.isEnabled = true
                val response = states.result
                if(response.hasError) {
                    alerta("Error inesperado", response.error!!.errorMessage, false, false, cancelable =  true)
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                bind.pbLogin.visibility = View.GONE
            }
        }
    }

    private fun listeners() {
        bind.btLogin.setOnClickListener {
            val email = bind.etUser.text.toString()
            val password = bind.etPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                alerta(
                    getString(R.string.error),
                    getString(R.string.msg_empty_field_login),
                    false,
                    flash = true
                )
            } else if (!validarCorreo(email)) {
                alerta(
                    getString(R.string.error),
                    getString(R.string.msg_invalid_email),
                    false,
                    flash = true
                )
            } else {
                viewModel.action(LoginViewModel.Actions.Login(
                    LoginBodyModel(
                        "MX",
                        email,
                        password,
                        "password",
                        "IATestCandidate",
                        "c840457e777b4fee9b510fbcd4985b68"
                    )
                ))
            }
        }
    }

    private fun validarCorreo(param1: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(param1).matches()
    }
}