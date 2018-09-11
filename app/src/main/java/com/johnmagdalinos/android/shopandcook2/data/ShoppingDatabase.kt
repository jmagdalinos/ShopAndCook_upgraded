package com.johnmagdalinos.android.shopandcook2.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [ShoppingEntry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        private const val DATABASE_NAME: String = "shopping"

        fun newInstance(context: Context) : ShoppingDatabase {
            return Room.databaseBuilder(context.applicationContext, ShoppingDatabase::class
                    .java, DATABASE_NAME).build()
        }
    }
}