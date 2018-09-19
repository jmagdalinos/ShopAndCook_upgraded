package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnmagdalinos.android.shopandcook2.R
import kotlinx.android.synthetic.main.color_list_item.view.*

class ColorAdapter(private val context: Context, var originalPosition: Int, val listener: (Int) -> Unit):
        RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private val colorList: Array<Int> = arrayOf<Int>(
            ContextCompat.getColor(context, R.color.ingredientType0),
            ContextCompat.getColor(context, R.color.ingredientType1),
            ContextCompat.getColor(context, R.color.ingredientType2),
            ContextCompat.getColor(context, R.color.ingredientType3),
            ContextCompat.getColor(context, R.color.ingredientType4),
            ContextCompat.getColor(context, R.color.ingredientType5))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.color_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = colorList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(
                position,
                colorList[position],
                originalPosition,
                listener)

        // Set the onClickListener
        holder.itemView.iv_color_list_color.setOnClickListener {
            listener(position)
            originalPosition = position
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindTo(position: Int, color: Int, originalPosition: Int, listener: (Int) -> Unit) {
            // Change the color of the circle
            val circleColor: GradientDrawable = itemView.iv_color_list_color.background as GradientDrawable
            circleColor.setColor(color)

            // Check if this color is the selected one. If so, display the hidden ImageView to
            // mark it as such
            if (adapterPosition == originalPosition) {
                itemView.iv_color_list_selected.visibility = View.VISIBLE
            } else itemView.iv_color_list_selected.visibility = View.INVISIBLE
        }
    }
}