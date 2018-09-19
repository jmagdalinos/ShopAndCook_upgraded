package com.johnmagdalinos.android.shopandcook2.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.johnmagdalinos.android.shopandcook2.data.Repository
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private var shoppingList: LiveData<List<ShoppingEntry>> = repository.getShoppingList(Pair("name",
            true))

    /** Initiate the list shopping list */
    fun init(sortPair: Pair<String, Boolean>) {
        shoppingList = repository.getShoppingList(sortPair)
    }

    fun reset(sortPair: Pair<String, Boolean>) {
        shoppingList = repository.getShoppingList(sortPair)
    }

    fun getShoppingList(): LiveData<List<ShoppingEntry>>? {
        return shoppingList
    }

    fun insertShoppingEntry(entry: ShoppingEntry){
        repository.insertShoppingEntry(entry)
    }

    fun updateShoppingEntry(entry: ShoppingEntry) {
        repository.updateShoppingEntry(entry)
    }

    fun deleteAllShopping() {
        repository.deleteAllShopping()
    }

    fun deleteShoppingEntry(entry: ShoppingEntry) {
        repository.deleteShoppingEntry(entry)
    }

    fun addTestEntry() {
        repository.insertRandomTestEntry()

    }

    fun addAllTestEntries() {
        repository.insertAllTestEntries()
    }
}