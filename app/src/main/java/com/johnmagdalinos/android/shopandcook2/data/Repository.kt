package com.johnmagdalinos.android.shopandcook2.data

import android.arch.lifecycle.LiveData
import com.johnmagdalinos.android.shopandcook2.AppExecutors
import java.util.*

class Repository {
    companion object {
        private lateinit var shoppingDao: ShoppingDao
        private lateinit var appExecutors: AppExecutors

        fun newInstance(shoppingDao: ShoppingDao, appExecutors: AppExecutors): Repository {
            val sr: Repository = Repository()
            synchronized(sr) {
                this.shoppingDao = shoppingDao
                this.appExecutors = appExecutors
            }

            return sr
        }
    }

    fun getShoppingList(): LiveData<List<ShoppingEntry>> {
        return shoppingDao.getAllShoppingASCName()
    }

    fun insertShoppingEntry(entry: ShoppingEntry) {
        appExecutors.diskIO().execute { shoppingDao.insertShoppingEntry(entry) }
    }

    fun insertMultipleShoppingEntries() {

//        appExecutors.diskIO().execute{shoppingDao.insertMultipleShoppingEntries(*testEntries.toTypedArray())}
    }

    // TODO: Only for testing
    fun insertRandomEntry() {
        val testEntries: ArrayList<ShoppingEntry> = ArrayList()
        testEntries.add(ShoppingEntry(null, "Potatoes", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Salt", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Pepper", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Flour", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Tomatoes", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Bread", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Bananas", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Apples", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Oranges", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Water bottles", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Butter", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Milk", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Soda", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Coca cola", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Lemonade", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Orange juice", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Pineapple", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Melon", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Tomato paste", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Berries", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Cherries", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Avocado", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Mayo", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Green Peppers", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Cucumbers", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Oregano", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Cocoa", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Clementines", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Salad", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Lettuce", null, null, null, 1, 0))


        appExecutors.diskIO().execute { shoppingDao.insertShoppingEntry(testEntries[Random()
                .nextInt(testEntries.size - 1)]) }

    }

    fun deleteShoppingEntry(entry: ShoppingEntry) {
        appExecutors.diskIO().execute { shoppingDao.deleteShoppingEntry(entry) }
    }
    fun deleteAllShopping() {
        appExecutors.diskIO().execute { shoppingDao.deleteAllShopping() }
    }
}