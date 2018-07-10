package com.johnmagdalinos.android.shopandcook2.ui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry

class DetailViewModel : ViewModel() {

    private val shoppingList: MutableLiveData<ArrayList<ShoppingEntry>> = MutableLiveData()

    fun getShoppingList(): MutableLiveData<ArrayList<ShoppingEntry>> = shoppingList

    fun insertShoppingEntry(entry: ShoppingEntry){
        // TODO: Insert Entry
    }

    fun updateShoppingEntry(entry: ShoppingEntry) {
        // TODO: Update entry
    }

    fun deleteAllShopping() {
        // TODO: Delete all
    }

    fun deleteShoppingEntry(entry: ShoppingEntry) {
        // TODO: Delete entry
    }

    fun addTestEntries() {
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

        deleteAllShopping()
        shoppingList.postValue(testEntries)
    }
}