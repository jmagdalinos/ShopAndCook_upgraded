package com.johnmagdalinos.android.shopandcook2.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import com.johnmagdalinos.android.shopandcook2.R

class Conversions {
    companion object {
        /** Returns true for 1 and false for 0 */
        fun convertIntToBoolean(int: Int?) : Boolean {
            return int == 1
        }

        /** Returns the measure based on the measure given in int */
        fun convertIntToMeasure(context: Context, measure: Int? = 0, quantity: Float? = 0.0f):
                String {

            val finalMeasure: Int = measure ?: 0
            val finalQuantity: Int = quantity?.toInt() ?: 0

            return when(finalMeasure) {
                1 -> context.resources.getQuantityString(R.plurals.measure_1, finalQuantity)
                2 -> context.resources.getQuantityString(R.plurals.measure_2, finalQuantity)
                3 -> context.resources.getQuantityString(R.plurals.measure_3, finalQuantity)
                4 -> context.resources.getQuantityString(R.plurals.measure_4, finalQuantity)
                5 -> context.resources.getQuantityString(R.plurals.measure_5, finalQuantity)
                6 -> context.resources.getQuantityString(R.plurals.measure_6, finalQuantity)
                7 -> context.resources.getQuantityString(R.plurals.measure_7, finalQuantity)
                8 -> context.resources.getQuantityString(R.plurals.measure_8, finalQuantity)
                9 -> context.resources.getQuantityString(R.plurals.measure_9, finalQuantity)
                10 -> context.resources.getQuantityString(R.plurals.measure_10, finalQuantity)
                11 -> context.resources.getQuantityString(R.plurals.measure_11, finalQuantity)
                12 -> context.resources.getQuantityString(R.plurals.measure_12, finalQuantity)
                else -> context.resources.getQuantityString(R.plurals.measure_0, finalQuantity)
            }
        }

        /** Returns the quantity in String format eliminating all unnecessary decimals  */
        fun convertQuantityToString(quantity: Float?) : String {
            if (quantity == null) return "0"
            // Round the quantity to 2 decimals
            val quantityRounded = Math.round(quantity * 100) / 100.0
            val intPart = quantityRounded.toInt()
            val decimals = quantityRounded - intPart

            return if (decimals == 0.0) {
                intPart.toString()
            } else {
                quantityRounded.toString()
            }
        }

        /** Returns the color of the item circle based on an int */
        fun convertIntToColor(context: Context, int: Int?) : Int {
            if (int == null) return ContextCompat.getColor(context, R.color.ingredientType0)
            return when (int) {
                0 -> ContextCompat.getColor(context, R.color.ingredientType0)
                1 -> ContextCompat.getColor(context, R.color.ingredientType1)
                2 -> ContextCompat.getColor(context, R.color.ingredientType2)
                3 -> ContextCompat.getColor(context, R.color.ingredientType3)
                4 -> ContextCompat.getColor(context, R.color.ingredientType4)
                else -> ContextCompat.getColor(context, R.color.ingredientType5)
            }
        }
    }
}