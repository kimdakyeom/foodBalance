package com.dkkim.anew.RecyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentDietBinding
import kotlinx.android.synthetic.main.item_diet.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DietAdapter(val dietList : ArrayList<FoodInfo>): RecyclerView.Adapter<DietAdapter.DietViewHolder>() {

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietAdapter.DietViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diet, parent, false)
        return DietViewHolder(view).apply {
            itemView.setOnClickListener {
//                val curPos: Int = adapterPosition
//                val foodInfo: FoodInfo = dietList.get(curPos)
//                Toast.makeText(
//                    parent.context,
//                    "${foodInfo.food_Name} ${foodInfo.serving_Weight} ${foodInfo.kcal} ${foodInfo.carbo} ${foodInfo.pro} ${foodInfo.fat}",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }
        Log.d("리사이클러뷰 가 불러짐","ㅇㅇㅇㅇ")
    }

    // View에 내용 입력
    override fun onBindViewHolder(holder: DietAdapter.DietViewHolder, position: Int) {
        val today = System.currentTimeMillis()
        val simpleTimeFormat = SimpleDateFormat("hh:mm", Locale.KOREAN).format(today)

        holder.food_name.text = dietList.get(position).food_Name
        holder.food_weight.text = dietList.get(position).serving_Weight.toString()
        holder.food_cal.text = dietList.get(position).kcal.toString()
        holder.food_time.text = simpleTimeFormat
    }

    // 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return dietList.size
    }

    inner class DietViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        // View와 데이터를 연결시키는 함수
        var food_name = itemview.findViewById<TextView>(R.id.food_name)
        var food_weight = itemview.findViewById<TextView>(R.id.food_weight)
        var food_cal = itemview.findViewById<TextView>(R.id.food_cal)
        var food_time = itemview.findViewById<TextView>(R.id.food_time)

    }

}
