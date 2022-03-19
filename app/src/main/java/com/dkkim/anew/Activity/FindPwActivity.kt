package com.dkkim.anew.Activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkkim.anew.R
import com.dkkim.anew.databinding.ActivityFindPwBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_find_pw.*


class FindPwActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityFindPwBinding

    private lateinit var findPw: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetPwBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reset_pw_btn -> {
                findPassword()
                findPw = findViewById(R.id.find_pw)
                val intent = Intent(this, FindPwResultActivity::class.java)
                intent.putExtra("email", findPw.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun findPassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(find_pw.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "findpassword:success")
                } else {
                    Log.d(task.exception.toString(), "findpassword:failure")
                }
            }
    }
}