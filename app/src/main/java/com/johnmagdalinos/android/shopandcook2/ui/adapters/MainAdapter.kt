package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainAdapter(private val context: Context, private val listener: (String, View) -> Unit): RecyclerView
.Adapter<MainAdapter.ViewHolder>() {

    private val listItems: Array<String> = context.resources.getStringArray(R.array.main_list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent,
                false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = 6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position], getDrawableForListItem(position), listener)
    }

    private fun getDrawableForListItem(position: Int): Drawable {
        return when(position) {
            0 -> context.getDrawable(R.drawable.shopping_list)
            1 -> context.getDrawable(R.drawable.meal_planner)
            2 -> context.getDrawable(R.drawable.recipes)
            3 -> context.getDrawable(R.drawable.meals)
            4 -> context.getDrawable(R.drawable.recipe_ideas)
            5 -> context.getDrawable(R.drawable.sign_out)
            else -> throw IllegalArgumentException("Wrong list item position")
        }
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String, image: Drawable, listener: (String, View) -> Unit) = with(title) {
            itemView.tv_main_list_item.text = title
            itemView.iv_main_list_item.setImageDrawable(image)
            itemView.setOnClickListener { listener(title, itemView.iv_main_list_item) }
        }
    }

}