package com.dkkim.anew.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.SettingInfo
import com.dkkim.anew.R

class SettingItemAdapter(private val settingList: ArrayList<SettingInfo>) :
    RecyclerView.Adapter<SettingItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.setting_item_name)
    }

    // 클릭 이벤트 커스텀 위함
    interface OnItemClickListener {
        fun onItemClick(view: View, data: SettingInfo, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 오버라이딩 해야하는 함수들

    // 리사이클러뷰 아이템 레이아웃 붙여주는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return ViewHolder(view)
    }

    // 레이아웃 내에 값 설정하는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = settingList[position].name

        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, settingList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return settingList.size
    }


}