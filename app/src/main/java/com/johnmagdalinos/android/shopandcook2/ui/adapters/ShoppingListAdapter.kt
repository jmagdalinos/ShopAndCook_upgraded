package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingListAdapter(val context: Context): RecyclerView
.Adapter<ShoppingListAdapter.ViewHolder>() {
    var listItems: ArrayList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.shopping_list_item,
                parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = listItems?.get(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.tv_shopping_list_item
    }

}