package com.johnmagdalinos.android.shopandcook2.data

import android.arch.lifecycle.LiveData
import com.johnmagdalinos.android.shopandcook2.AppExecutors
import java.util.*
import kotlin.collections.ArrayList

class Repository {
    companion object {
        private lateinit var shoppingDao: ShoppingDao
        private lateinit var appExecutors: AppExecutors

        fun newInstance(shoppingDao: ShoppingDao, appExecutors: AppExecutors): Repository {
            val sr = Repository()
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

    fun insertMultipleShoppingEntries(entries: ArrayList<ShoppingEntry>) {
        appExecutors.diskIO().execute{ shoppingDao.insertMultipleShoppingEntries(*entries
                .toTypedArray()) }
    }

    fun updateShoppingEntry(entry: ShoppingEntry) {
        appExecutors.diskIO().execute { shoppingDao.updateShoppingEntry(entry) }
    }

    // TODO: Remove after testing
    fun insertRandomTestEntry() {
        val testEntries : ArrayList<ShoppingEntry> = createRandomEntries()
        insertShoppingEntry(testEntries[Random().nextInt(testEntries.size - 1)])
    }

    fun insertAllTestEntries() {
        val testEntries : ArrayList<ShoppingEntry> = createRandomEntries()
        insertMultipleShoppingEntries(testEntries)
    }

    fun deleteShoppingEntry(entry: ShoppingEntry) {
        appExecutors.diskIO().execute { shoppingDao.deleteShoppingEntry(entry) }
    }
    fun deleteAllShopping() {
        appExecutors.diskIO().execute { shoppingDao.deleteAllShopping() }
    }

    private fun createRandomEntries() : ArrayList<ShoppingEntry> {
        val testEntries: ArrayList<ShoppingEntry> = ArrayList()
        testEntries.add(ShoppingEntry(null, "Potatoes", null, 0, 2.0, 1, 0))
        testEntries.add(ShoppingEntry(null, "Salt", null, 1, 2.7, 5, 1))
        testEntries.add(ShoppingEntry(null, "Pepper", null, 2, 62.0, 4, 1))
        testEntries.add(ShoppingEntry(null, "Flour", null, 3, 22.5058, 3, 0))
        testEntries.add(ShoppingEntry(null, "Tomatoes", null, 4, null, 2, 1))
        testEntries.add(ShoppingEntry(null, "Bread", null, 5, 52.548, 1, 0))
        testEntries.add(ShoppingEntry(null, "Bananas", null, 6, null, 0, 1))
        testEntries.add(ShoppingEntry(null, "Apples", null, 7, 32.2205, 5, 0))
        testEntries.add(ShoppingEntry(null, "Oranges", null, 8, 0.2235, 4, 1))
        testEntries.add(ShoppingEntry(null, "Water bottles", null, 9, null, 3, 1))
        testEntries.add(ShoppingEntry(null, "Butter", null, 10, null, 2, 1))
        testEntries.add(ShoppingEntry(null, "Milk", null, 11, 32.25, 1, 1))
        testEntries.add(ShoppingEntry(null, "Soda", null, 12, null, 0, 0))
        testEntries.add(ShoppingEntry(null, "Coca cola", null, 0, 0.2526, 5, 0))
        testEntries.add(ShoppingEntry(null, "Lemonade", null, 1, 12.52587, 4, 0))
        testEntries.add(ShoppingEntry(null, "Orange juice", null, 2, null, 3, 0))
        testEntries.add(ShoppingEntry(null, "Pineapple", null, 3, null, 2, 0))
        testEntries.add(ShoppingEntry(null, "Melon", null, 4, null, 1, 1))
        testEntries.add(ShoppingEntry(null, "Tomato paste", null, 5, 3.568, 0, 1))
        testEntries.add(ShoppingEntry(null, "Berries", null, 6, null, 5, 0))
        testEntries.add(ShoppingEntry(null, "Cherries", null, 7, 750.0, 5, 0))
        testEntries.add(ShoppingEntry(null, "Avocado", null, 8, 32.0, 4, 1))
        testEntries.add(ShoppingEntry(null, "Mayo", null, 9, null, 4, 1))
        testEntries.add(ShoppingEntry(null, "Green Peppers", null, 10, 0.0, 3, 0))
        testEntries.add(ShoppingEntry(null, "Cucumbers", null, 11, 4.526, 3, 0))
        testEntries.add(ShoppingEntry(null, "Oregano", null, 12, 3.258, 2, 1))
        testEntries.add(ShoppingEntry(null, "Cocoa", null, null, 12.52, 2, 0))
        testEntries.add(ShoppingEntry(null, "Clementines", null, null, null, 1, 0))
        testEntries.add(ShoppingEntry(null, "Salad", null, null, 0.0, 1, 1))
        testEntries.add(ShoppingEntry(null, "Lettuce", null, null, 524.526, 0, 1))

        return testEntries
    }
}