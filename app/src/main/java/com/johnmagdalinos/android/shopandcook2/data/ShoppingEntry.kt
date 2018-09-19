package com.johnmagdalinos.android.shopandcook2.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "shopping",
        indices = [(Index("name")), (Index("color")), (Index("checked"))])
data class ShoppingEntry (
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        var name: String,
        var notes: String?,
        var measure: Int?,
        var quantity: Float?,
        var color: Int,
        var checked: Int)