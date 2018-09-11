package com.johnmagdalinos.android.shopandcook2.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.johnmagdalinos.android.shopandcook2.data.Repository
import com.johnmagdalinos.android.shopandcook2.data.ShoppingEntry

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private var shoppingList: LiveData<List<ShoppingEntry>>? = null

    /** Initiate the list shopping list */
    fun init() {
        shoppingList = repository.getShoppingList()
    }

    fun getShoppingList(): LiveData<List<ShoppingEntry>>? {
      return shoppingList
    }

    fun insertShoppingEntry(entry: ShoppingEntry){
        repository.insertRandomEntry()
    }

    fun updateShoppingEntry(entry: ShoppingEntry) {
        // TODO: Update entry
    }

    fun deleteAllShopping() {
        repository.deleteAllShopping()
    }

    fun deleteShoppingEntry(entry: ShoppingEntry) {
        repository.deleteShoppingEntry(entry)
    }

    fun addTestEntries() {
        repository.insertRandomEntry()
    }
}