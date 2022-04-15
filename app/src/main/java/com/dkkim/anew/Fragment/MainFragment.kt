package com.dkkim.anew.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.Activity.LoginActivity
import com.dkkim.anew.Activity.MainApplication
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.UserAccount
import com.dkkim.anew.R
import com.dkkim.anew.Util.MySharedPreferences
import com.dkkim.anew.databinding.FragmentMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainFragment: Fragment() {
    private val data: Intent? = null
    lateinit var binding: FragmentMainBinding // 프래그먼트 바인딩
    private val retrofit = RetrofitClient.create() // 레트로핏 클라이언트 선언
    private lateinit var firebaseAuth: FirebaseAuth

    val TAG : String = "Test"

    var user = UserAccount(Firebase.auth.currentUser?.uid, null, null)

    private var food_Name: String = ""
    private var service_Weight: Double = 0.0
    private var kcal: Double = 0.0
    private var carbo: Double = 0.0
    private var pro: Double = 0.0
    private var fat: Double = 0.0
    private var food_Time: String = ""
    private var mWorkerThread : Thread? = null

    private var dietMode = "basic"

    // 공공데이터 open api 디코딩키 선언
    private val foodNutriDecodingKey =
        "j/xkShPJBtxFbK+ahZ+zy8yx8hTGU36HJbFQ9ZK0/JNRG6yhX41qMmiyl73Z1VSpfFZUiK3DBt31s9qnfHqLEw=="

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView( // onCreate 후에 화면을 구성할 때 호출
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? // Layout 가져오기
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false) // 레이아웃을 MainFragment에 붙히는 부분

        dietMode = MySharedPreferences.getDietMode(requireContext())

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result


        })

        UserNameInfo(user.uid.toString())
        // 검색창
        binding.search.setOnClickListener {

            val intent = Intent(requireContext(), FoodSearchActivity::class.java)
            startActivityForResult(intent, 200)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        when(dietMode){
            "minus" -> {
                binding.dietModeText.text = "감량모드"
            }
            "basic" -> {
                binding.dietModeText.text = "유지모드"
            }
            "add" -> {
                binding.dietModeText.text = "증량모드"
            }
        }

        binding.saveBtn.setOnClickListener {

            putInfo(food_Name, service_Weight, kcal, carbo, pro, fat, food_Time)

            Toast.makeText(context, "데이터가 저장되었습니다", Toast.LENGTH_SHORT)
                .show()
        }

        binding.dietModeText.setOnClickListener {
            parentFragmentManager.beginTransaction() // 프래그먼트 트랜잭션 시작
                .addToBackStack(null) // 스택에 프래그먼트 쌓기
                .replace(R.id.main_frame, SettingDietModeFragment()) // (프래그먼트에 들어갈 프레임 레이아웃 ID, 전환될 프래그먼트)
                .commit() // 실행
        }

        binding.directAddBtn.setOnClickListener {

            val dialogListener = DialogInterface.OnClickListener { dialogInterface, i ->
                var alert = dialogInterface as AlertDialog
                var foodWeightEdit = alert.findViewById<EditText>(R.id.direct_add_food_weight)
                binding.foodWeight.text = foodWeightEdit.text

            }

            val builder = AlertDialog.Builder(requireContext())
            builder.apply {
                setTitle("음식량(g) 직접 입력")
                setIcon(R.drawable.ic_baseline_border_color_24)
                val view = layoutInflater.inflate(R.layout.dialog_direct_add, null)
                setView(view)
                setPositiveButton("입력", dialogListener)
                setNegativeButton("취소",null)
                show()
            }


        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        if(MainApplication.m_bluetoothSocket != null){
            beginListenForData()
        }
    }

    override fun onStop() {
        super.onStop()
        mWorkerThread?.interrupt()
        mWorkerThread = null
    }

    /**
     * 블루투스 데이터 수신 Listener
     */
    fun beginListenForData() {
        if(MainApplication.mmInStream == null){
            return
        }
        mWorkerThread = Thread {
            while (!Thread.currentThread().isInterrupted) {

                try {
                    val bytesAvailable = MainApplication.mmInStream?.available()
                    if (bytesAvailable != null) {
                        if (bytesAvailable > 0) { //데이터가 수신된 경우
                            val packetBytes = ByteArray(bytesAvailable)
                            MainApplication.mmInStream?.read(packetBytes)
                            // 한 버퍼 처리
                            // Byte -> String
                            val scale = String(packetBytes,Charsets.UTF_8)
                            //수신 String 출력
                            // Toast.makeText(context, "수신: $scale", Toast.LENGTH_SHORT).show()
                            Thread.sleep(1000)
                            activity?.runOnUiThread {
                                binding.foodWeight.text = scale
                            }
                             /**한 바이트씩 처리
                           for (i in 0 until bytesAvailable) {
                                val b = packetBytes[i]
                                Log.d("inputData", String.format("%02x", b))
                           } */
                        }
                    }
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        //데이터 수신 thread 시작
        mWorkerThread!!.start()

    }


    // main activity에서 sub activity를 호출해서 넘어갔다가 다시 main activity로 돌아옴
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    { // data는 intent로 받음
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) { // FoodSearchActivity이면
            if (resultCode == Activity.RESULT_OK) { // 음식 검색결과 액티비티에에서 선택한 음식명
                // 받아온 데이터 이름, 1회 제공량, 칼로리, 탄, 단, 지 저장
                food_Name = data?.getStringExtra("food_Name").toString()
                service_Weight = data!!.getDoubleExtra("service_Weight", 0.0)
                kcal = data.getDoubleExtra("kcal", 0.0)
                carbo = data.getDoubleExtra("carbo", 0.0)
                pro = data.getDoubleExtra("pro", 0.0)
                fat = data.getDoubleExtra("fat", 0.0)

                binding.foodWeight.text = service_Weight.toString()
                binding.calG.text = kcal.toString()
                binding.carG.text = carbo.toString()
                binding.proG.text = pro.toString()
                binding.fatG.text = fat.toString()

                binding.textSearch.text = food_Name

                super.onActivityResult(requestCode, resultCode, data)

                System.out.println("kcal$kcal")

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun putInfo(food_Name: String, service_Weight: Double, kcal: Double, carbo: Double, pro: Double, fat: Double, food_Time: String){
        val mDatabase = FirebaseDatabase.getInstance().reference

        val today = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(today)

        val food_Time = SimpleDateFormat("HH:mm", Locale.KOREAN).format(today)

        val foodInfo = FoodInfo(
            food_Name,
            service_Weight,
            kcal,
            carbo,
            pro,
            fat,
            food_Time
        )

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = db.getReference("UserAccount")
        mDatabase.child("UserAccount").child(Firebase.auth.currentUser!!.uid.toString()).child(simpleDateFormat).push().setValue(foodInfo)
    }

    private fun UserNameInfo(uid: String) {
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (snapshotChild in snapshot.children) {
                    val user = snapshotChild.getValue()

                    var name = snapshotChild.child("name").getValue().toString()

                    binding.name.text = name
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Log.w("Error", "Failed to read value.", error.toException())
            }
        })
    }
}