package mx.com.ia.cinemorelia.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import mx.com.ia.cinemorelia.databinding.ActivitySplashBinding
import mx.com.ia.cinemorelia.features.login.ui.LoginActivity
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.key_access_token
import mx.com.ia.cinemorelia.service.CinemoreliaSharedPreference
import mx.com.ia.cinemorelia.ui.CinemoreliaActivity
import mx.com.ia.cinemorelia.ui.MainActivity
import org.koin.android.ext.android.inject

class SplashActivity: CinemoreliaActivity() {
    private lateinit var bind: ActivitySplashBinding
    private val sharedPreference: CinemoreliaSharedPreference by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        validateUserLogged()
    }

    private fun validateUserLogged() {
        val token = sharedPreference.getStringValue(key_access_token)
        val timer = object: CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }
            override fun onFinish() {
                if (token != null) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            }
        }
        timer.start()
    }

}