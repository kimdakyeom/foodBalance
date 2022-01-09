package com.dkkim.anew.RecyclerView

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.ResultFoodCodeList
import com.dkkim.anew.R


class FoodResultAdapter(
    private val resultList: MutableList<FoodCodeInfo>
) : RecyclerView.Adapter<FoodResultAdapter.FoodResultViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(view: View, data: FoodCodeInfo, position: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class FoodResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodName: TextView

        init {
            foodName = itemView.findViewById<TextView>(R.id.textView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodResultViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.food_result_item, parent, false)
        return FoodResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodResultViewHolder, position: Int) {
        holder.foodName.text = resultList[position].foodName

        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, resultList[position], position)
            }
        }


    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}
