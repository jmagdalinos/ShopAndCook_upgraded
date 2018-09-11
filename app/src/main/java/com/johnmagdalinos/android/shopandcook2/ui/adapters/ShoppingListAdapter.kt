package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingListAdapter(val context: Context): ListAdapter<ShoppingEntry,
        ShoppingListAdapter.ViewHolder>(DIFF_CALLBACK) {

    var list: List<ShoppingEntry>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.shopping_list_item,
                parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun submitList(list: List<ShoppingEntry>?) {
        this.list = list
        super.submitList(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(entry: ShoppingEntry?) {
            itemView.tv_shopping_list_item.text = entry?.name
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShoppingEntry>() {

            override fun areItemsTheSame(oldItem: ShoppingEntry?, newItem: ShoppingEntry?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: ShoppingEntry?, newItem: ShoppingEntry?): Boolean {
                return oldItem == newItem
            }
        }
    }


}