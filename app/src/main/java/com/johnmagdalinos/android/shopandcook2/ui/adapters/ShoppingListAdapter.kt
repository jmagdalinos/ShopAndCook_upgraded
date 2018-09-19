package com.johnmagdalinos.android.shopandcook2.ui.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.johnmagdalinos.android.shopandcook2.R
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry
import com.johnmagdalinos.android.shopandcook2.utils.Conversions.Companion.convertIntToBoolean
import com.johnmagdalinos.android.shopandcook2.utils.Conversions.Companion.convertIntToColor
import com.johnmagdalinos.android.shopandcook2.utils.Conversions.Companion.convertIntToMeasure
import com.johnmagdalinos.android.shopandcook2.utils.Conversions.Companion.convertQuantityToString
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingListAdapter(val context: Context, private val listener: (Int, Boolean, Boolean) ->
Unit): ListAdapter<ShoppingEntry, ShoppingListAdapter.ViewHolder>(DIFF_CALLBACK) {

    var list: List<ShoppingEntry>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.shopping_list_item,
                parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTo(context, getItem(position), listener)

    /** Used to submit a new list. Starts DiffUtil */
    override fun submitList(list: List<ShoppingEntry>?) {
        this.list = list
        super.submitList(list)
    }

    /** Used to retrieve the item at a specific position */
    fun getItemAtPosition(position: Int) : ShoppingEntry? = if (list!= null) list!![position] else null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(context: Context, entry: ShoppingEntry?, listener: (Int, Boolean, Boolean) ->
        Unit) {
            itemView.chk_shopping_list.isChecked = convertIntToBoolean(entry?.checked)
            itemView.tv_shopping_list_item_name.text = entry?.name

            // Hide the quantity and measure if the quantity is empty
            if (entry?.quantity == null || entry.quantity == 0.0f) {
                itemView.tv_shopping_list_item_quantity.visibility = View.INVISIBLE
                itemView.tv_shopping_list_item_measure.visibility = View.INVISIBLE
            } else {
                itemView.tv_shopping_list_item_quantity.text = convertQuantityToString(entry.quantity)
                itemView.tv_shopping_list_item_measure.text = convertIntToMeasure(context, entry.measure, entry.quantity)
            }

            // Grey out the views if they're checked
            greyOutViews(itemView.chk_shopping_list.isChecked, false, context)

            // Set the color of the circle
            val circleColor: GradientDrawable = itemView.iv_shopping_list_color.background as GradientDrawable
            circleColor.setColor(convertIntToColor(context, entry?.color))

            // Setup the onClickListener for the checkBox
            itemView.chk_shopping_list.setOnClickListener {
                listener(adapterPosition, itemView.chk_shopping_list.isChecked, false)
                // Grey out the checked ViewHolder
                greyOutViews(itemView.chk_shopping_list.isChecked, true, context)}

            // Setup the onClickListener for the entire ViewHolder
            itemView.setOnClickListener {
                listener(adapterPosition, false, true)
            }
        }

        /** Used to grey out the views of the ViewHolder if its checkbox is checked */
        private fun greyOutViews(checked: Boolean, animate: Boolean, context: Context) {
            if (checked) with(itemView){
                // Grey out the views
                chk_shopping_list.alpha = 0.5f
                tv_shopping_list_item_name.alpha = 0.5f
                tv_shopping_list_item_quantity.alpha = 0.5f
                tv_shopping_list_item_measure.alpha = 0.5f
                iv_shopping_list_color.alpha = 0.5f

                if (animate) {
                    iv_shopping_list_pencil.alpha = 1.0f
                    iv_shopping_list_pencil_line.alpha = 1.0f
                    runPencilAnimation(context)
                } else {
                    iv_shopping_list_pencil_line.alpha = 1.0f
                }
            } else with(itemView) {
                // Show the views and hide the line
                chk_shopping_list.alpha = 1.0f
                tv_shopping_list_item_name.alpha = 1.0f
                tv_shopping_list_item_quantity.alpha = 1.0f
                tv_shopping_list_item_measure.alpha = 1.0f
                iv_shopping_list_color.alpha = 1.0f
                iv_shopping_list_pencil_line.alpha = 0.0f
            }
        }

        /** Used to move pencil to erase the ViewHolder */
        private fun runPencilAnimation(context: Context) {
            // Move the pencil
            val moveAnimator: Animator = ObjectAnimator.ofFloat(
                    itemView.iv_shopping_list_pencil,
                    "translationX",
                    0f,
                    itemView.iv_shopping_list_color.x - 2 * itemView.iv_shopping_list_color.width
            )
            moveAnimator.duration = 1000
            moveAnimator.interpolator = AccelerateDecelerateInterpolator()

            // Fade in the pencil
            val visibilityAnimator: Animator = ObjectAnimator.ofFloat(
                    itemView.iv_shopping_list_pencil,
                    "alpha",
                    1.0f,
                    0.0f)
            visibilityAnimator.startDelay = 1000
            visibilityAnimator.duration = 100

            // Animate the line
            val animatedPencilLine: AnimatedVectorDrawable = context.getDrawable(R.drawable
                    .pencil_line_animated) as AnimatedVectorDrawable
            itemView.iv_shopping_list_pencil_line.setImageDrawable(animatedPencilLine)

            moveAnimator.start()
            visibilityAnimator.start()
            animatedPencilLine.start()
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