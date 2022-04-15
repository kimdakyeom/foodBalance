package com.dkkim.anew.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DietAdapter(private val dietList: ArrayList<FoodInfo>) :
    RecyclerView.Adapter<DietAdapter.DietViewHolder>() {

    interface OnItemClickListener { // RecyclerView 외부 클릭이벤트
        fun onItemClick(view: View, data: FoodInfo, position: Int)
    }

    private var listener: DietAdapter.OnItemClickListener? = null // 리스너 객체 참조를 저장하는 변수
    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: DietAdapter.OnItemClickListener) {
        this.listener = listener
    }

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietAdapter.DietViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diet, parent, false)

        return DietViewHolder(view)
    }

    // View에 내용 입력
    override fun onBindViewHolder(holder: DietAdapter.DietViewHolder, position: Int) {
        val today = System.currentTimeMillis()
        val simpleTimeFormat = SimpleDateFormat("hh:mm", Locale.KOREAN).format(today)

        holder.food_name.text = dietList.get(position).food_Name
        holder.food_weight.text = dietList.get(position).serving_Weight.toString()
        holder.food_cal.text = dietList.get(position).kcal.toString()
        holder.food_time.text = dietList.get(position).food_Time.toString()

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