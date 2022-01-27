package com.dkkim.anew.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.Item
import com.dkkim.anew.R
import kotlinx.android.synthetic.main.item_diet.view.*

class DietAdapter(private val dietList: ArrayList<FoodInfo>):
    RecyclerView.Adapter<DietAdapter.DietViewHolder>() {
    // 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(view: View, data: FoodInfo, position: Int)
    }
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var food_Name: TextView
        var food_Weight: TextView
        var cal: TextView
        var car: TextView
        var pro: TextView
        var fat: TextView

        init {
            food_Name = itemView.findViewById<TextView>(R.id.food_name)
            food_Weight = itemView.findViewById<TextView>(R.id.food_weight)
            cal = itemView.findViewById<TextView>(R.id.food_cal)
            car = itemView.findViewById<TextView>(R.id.food_car)
            pro = itemView.findViewById<TextView>(R.id.food_pro)
            fat = itemView.findViewById<TextView>(R.id.food_fat)
        }
    }

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_diet, parent, false)
        return DietViewHolder(view)
    }


    // 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return dietList.size
    }


    // View에 내용 입력
    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        holder.food_Name.text = dietList[position].food_Name
        holder.food_Weight.text = dietList[position].serving_Weight.toString()
        holder.cal.text = dietList[position].kcal.toString()
        holder.car.text = dietList[position].carbo.toString()
        holder.pro.text = dietList[position].pro.toString()
        holder.fat.text = dietList[position].fat.toString()


        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, dietList[position], position)
            }
        }
    }

}