package com.dkkim.anew.Activity

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkkim.anew.Model.UserAccount
import com.dkkim.anew.databinding.ActivityJoinBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액션 바 등록
        val actionBar = getSupportActionBar()
        actionBar?.apply { // .apply -> 한번에 적용
            setTitle("회원가입")
            setDisplayHomeAsUpEnabled(true) // 뒤로가기버튼
            setDisplayShowHomeEnabled(true) // 홈아이콘
        }

        firebaseAuth = FirebaseAuth.getInstance()

        // 가입버튼 -> firebase에 데이터 저장
        binding.joinBtn.setOnClickListener {
            val email: String = binding.joinEmail.text.toString().trim()
            val pwd1: String = binding.joinPw1.text.toString().trim()
            val pwd2: String = binding.joinPw2.text.toString().trim() // 비밀번호확인란

            if (pwd1 == pwd2) {
                val dialog = ProgressDialog(this@JoinActivity)
                dialog.apply {
                    setMessage("가입중입니다.")
                    show()
                }

                firebaseAuth.createUserWithEmailAndPassword(email, pwd1)
                    .addOnCompleteListener(this@JoinActivity) { task ->
                        if (task.isSuccessful) { // 가입성공시
                            val user: FirebaseUser? = firebaseAuth.currentUser
                            val email: String? = user?.email
                            val uid: String? = user?.uid
                            val name = binding.joinName.text.toString().trim()

                            val account = UserAccount(
                                uid,
                                email,
                                name,
                                pwd1
                            ) // UserAccount 모댈 변수(uid, email, name, pwd 순)

                            val db: FirebaseDatabase = FirebaseDatabase.getInstance()
                            val reference: DatabaseReference = db.getReference("UserAccount")
                            reference.child(uid.toString()).setValue(account)

                            // 가입성공시 join액티비티 빠져나와 login액티비티로
                            val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@JoinActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                                .show()

                        } else { // 가입 실패시
                            Toast.makeText(
                                this@JoinActivity,
                                "이미 존재하는 아이디입니다.\n다시 시도해주세요", Toast.LENGTH_SHORT
                            ).show()
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                        }
                    }
            } else {
                Toast.makeText(
                    this@JoinActivity,
                    "비밀번호가 일치하지 않습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT
                ).show()
            }

        }

// 뒤로가기 버튼 클릭 이벤트
        binding.joinBackBtn.setOnClickListener {
            // 다시 로그인 화면 띄우기
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}