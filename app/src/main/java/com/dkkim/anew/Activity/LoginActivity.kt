package com.dkkim.anew.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkkim.anew.R
import com.dkkim.anew.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.SharedPreferences

import android.app.Activity
import android.content.Context
import com.dkkim.anew.Util.MySharedPreferences
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding // ActivityLogin 바인딩
    private var firebaseAuth = FirebaseAuth.getInstance() // FirebaseAuth 인스턴스 초기화


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭 이벤트
        binding.joinBtn.setOnClickListener(this)
        binding.loginBtn.setOnClickListener(this)
        binding.findPwBtn.setOnClickListener(this)


    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.find_pw_btn -> {
                val intent = Intent(this, FindPwActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.join_btn -> {
                // 회원가입 페이지로
                val intent = Intent(this, JoinActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.login_btn -> {
                val email = binding.loginEmail.text.toString() // 아이디
                val pwd = binding.loginPw.text.toString() // 비밀번호
                login(email, pwd, this@LoginActivity) // 로그인 함수 호출
            }
        }
    }

    fun login(email: String, pwd: String, myContext: Context) {
        firebaseAuth.signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener(
                this@LoginActivity
            ) { task ->
                if (task.isSuccessful) { // 로그인 성공 -> 메인액티비티로
                    Log.d(TAG, "signInWithEmail:success")

                    MySharedPreferences.setEmail(myContext, email, pwd)

                    if(myContext == this@LoginActivity) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, "로그인성공!", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else { // 로그인 오류 시
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this@LoginActivity, "오류가 발생했습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            }

    }
}


