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
import com.dkkim.anew.Model.UserInfo
import com.dkkim.anew.databinding.ActivityJoinBinding
import com.dkkim.anew.databinding.ActivitySecJoinBinding

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


class SecJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecJoinBinding
    private lateinit var firebaseAuth: FirebaseAuth // firebaseAuth 인스턴스 선언




    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecJoinBinding.inflate(layoutInflater) // ActivityJoin 바인딩
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance() // FirebaseAuth 인스턴스 초기화


        // 가입버튼 -> firebase에 데이터 저장
        binding.secJoinBtn.setOnClickListener {

            val name: String = binding.joinName.text.toString()
            val sex: Boolean = binding.joinSexFemale.isChecked
            val phone: String = binding.joinPhone.text.toString()
            val birth: String = binding.joinBirth.text.toString()
            val height: String = binding.joinHeight.text.toString()
            val weight: String = binding.joinHeight.text.toString()

            putUser(name, sex, phone, birth, height, weight)

        }


    }

    private fun putUser(
        name: String,
        sex: Boolean,
        phone: String,
        birth: String,
        height: String,
        weight: String
    ) {
        val mDatabase = FirebaseDatabase.getInstance().reference

        val userInfo = UserInfo(
            name,
            sex,
            phone,
            birth,
            height,
            weight
        )

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = db.getReference("UserAccount")
        mDatabase.child("UserAccount").child(Firebase.auth.currentUser!!.uid.toString()).child("info").setValue(userInfo)

        // 가입성공시 join액티비티 빠져나와 login액티비티로
        val intent = Intent(this@SecJoinActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this@SecJoinActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
            .show()

    }
}