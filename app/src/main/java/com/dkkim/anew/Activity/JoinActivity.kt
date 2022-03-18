package com.dkkim.anew.Activity

import android.annotation.SuppressLint
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_calendar_result.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import android.util.Log.d as Log


class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding

    private lateinit var firebaseAuth: FirebaseAuth // firebaseAuth 인스턴스 선언


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinBinding.inflate(layoutInflater) // ActivityJoin 바인딩
        setContentView(binding.root)

        // 액션 바 등록
        val actionBar = getSupportActionBar()
        actionBar?.apply { // .apply -> 한번에 적용
            setTitle("회원가입")
            setDisplayHomeAsUpEnabled(true) // 뒤로가기버튼
            setDisplayShowHomeEnabled(true) // 홈아이콘
        }

        firebaseAuth = FirebaseAuth.getInstance() // FirebaseAuth 인스턴스 초기화


        // 가입버튼 -> firebase에 데이터 저장
        binding.joinBtn.setOnClickListener {
            val email: String = binding.joinEmail.text.toString()
            val pwd1: String = binding.joinPw1.text.toString()
            val pwd2: String = binding.joinPw2.text.toString() // 비밀번호확인란


            if (pwd1 == pwd2) { // 비밀번호 입력란과 비밀번호 확인란이 같을 때

                // firebaseAuth에 email과 password로 user 생성
                firebaseAuth.createUserWithEmailAndPassword(email, pwd1)
                    .addOnCompleteListener(this@JoinActivity) { task -> // 성공유무 값 확인
                        if (task.isSuccessful) { // 성공시
                            val user: FirebaseUser? = firebaseAuth.currentUser
                            val email: String? = user?.email
                            val uid: String? = user?.uid

                            // UserAccount에 값 넣기
                            val userAccount = UserAccount(
                                uid,
                                email,
                                pwd1
                            ) // UserAccount 모댈 변수

                            val db: FirebaseDatabase = FirebaseDatabase.getInstance() // FirebaseDatabase 인스턴스 초기화
                            val reference: DatabaseReference = db.getReference("UserAccount") // DatabaseReference를 매개체 삼아 읽기/쓰기
                            reference.child(uid.toString()).child("account").setValue(userAccount) // reference에서 하위 값의 uid를 account에 즉시 값 변경

                            // 다음 누르면 SecJoinActivity로
                            val intent = Intent(this@JoinActivity, SecJoinActivity::class.java)
                            startActivity(intent)

                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) { // 가입 실패시
                                Toast.makeText(
                                    this@JoinActivity,
                                    "이메일이 중복됩니다.", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            } else { // 비밀번호란과 비밀번호 확인란이 일치하지 않을 때
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