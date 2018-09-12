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
        fun convertIntToMeasure(context: Context, measure: Int?, quantity: Double?) : String? {
            if (measure == null) return null

            val measuresSingular: Array<String> = context.resources.getStringArray(R.array
                    .measure_spinner_singular)
            val measuresPlural: Array<String> = context.resources.getStringArray(R.array.measure_spinner_plural)

            // Check if the measure should be returned in singular or plural form
            return if (quantity != null && quantity > 1) {
                measuresPlural[measure]
            } else {
                measuresSingular[measure]
            }

        }

        /** Returns the quantity in String format eliminating all unnecessary decimals  */
        fun convertQuantityToString(quantity: Double?) : String {
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