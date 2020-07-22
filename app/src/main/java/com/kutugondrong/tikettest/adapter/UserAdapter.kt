package com.kutugondrong.tikettest.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.kutugondrong.network.model.Item
import com.kutugondrong.tikettest.R
import kotlinx.android.synthetic.main.adapter_user.view.*

class UserAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<Item>()

    inner class ViewHolder(val  parent: ViewGroup) : BaseHolder(R.layout.adapter_user, parent){

        fun bind(data: Item){
            itemView.txtUser.text = data.full_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]
        val viewHolder = holder as ViewHolder

        viewHolder.bind(item)
    }

    fun initData(items: ArrayList<Item>){
        this.items = items
        notifyDataSetChanged()
    }


}
