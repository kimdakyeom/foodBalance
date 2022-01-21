package com.dkkim.anew.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R

class DietAdapter(
    private val resultList: MutableList<FoodInfo> // 읽기/쓰기가 가능한 list
) : RecyclerView.Adapter<DietAdapter.DietViewHolder>() { // RecyclerVew에 Adapter 정의

    interface OnItemClickListener { // RecyclerView 외부 클릭이벤트
        fun onItemClick(view: View, data: FoodInfo, position: Int)
    }

    private var listener: OnItemClickListener? = null // 리스너 객체 참조를 저장하는 변수
    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 한 화면에 나오는 레이아웃 개수만큼 생성, 새로 생성시 ViewHolder 재사용
    class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodName: TextView

        init {
            foodName =
                itemView.findViewById<TextView>(R.id.food_name)
        }

    }

    // ViewHolder 생성(레이아웃 생성)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_food_result, parent, false)
        return DietViewHolder(view)
    }

    // ViewHolder가 재활용될 때 실행되는 메서드
    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        holder.foodName.text = resultList[position].food_Name

        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, resultList[position], position)
            }
        }
    }

    // 아이템 개수를 조회
    override fun getItemCount(): Int {
        return resultList.size
    }
}