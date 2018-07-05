package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainAdapter(private val context: Context, val listener: (String) -> Unit): RecyclerView
.Adapter<MainAdapter.ViewHolder>() {
    private val listItems: Array<String> = context.resources.getStringArray(R.array.main_list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String, listener: (String) -> Unit) = with(title) {
            itemView.tv_list_item_no.text = title
            itemView.setOnClickListener { listener(title) }
        }
    }
}