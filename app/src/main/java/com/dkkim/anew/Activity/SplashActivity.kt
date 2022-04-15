package com.dkkim.anew.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dkkim.anew.databinding.ActivitySplashBinding
import android.app.Activity

import android.content.SharedPreferences
import android.widget.Toast
import com.dkkim.anew.Util.MySharedPreferences


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    var handler = Handler() // 딜레이 줄 때

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (MySharedPreferences.getEmail(this).isBlank()) {
            val login = Intent(this, LoginActivity::class.java)
            loadSplashScreen(login)

        } else { // sharedPreference 안에 토큰 저장되어 있을 때 -> Main

            Toast.makeText(this, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            val loginActivity = LoginActivity()
            loginActivity.login(
                MySharedPreferences.getEmail(this),
                MySharedPreferences.getPwd(this),
                this
            )

            val main = Intent(this, MainActivity::class.java)
            loadSplashScreen(main)

        }


    }
    private fun loadSplashScreen(intent: Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}