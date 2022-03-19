package com.dkkim.anew.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkkim.anew.databinding.ActivityFindPwBinding
import com.dkkim.anew.databinding.ActivityFindPwResultBinding
import kotlinx.android.synthetic.main.activity_find_pw_result.*
import kotlinx.android.synthetic.main.fragment_setting_qna.*

class FindPwResultActivity :  AppCompatActivity() {

    lateinit var binding: ActivityFindPwResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindPwResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("email")) {
            find_email.text = intent.getStringExtra("email")
        } else {
            Toast.makeText(this, "전달된 이메일이 없습니다", Toast.LENGTH_SHORT).show()
        }

        binding.findPwBtn.setOnClickListener{
            val intent = Intent(this@FindPwResultActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}