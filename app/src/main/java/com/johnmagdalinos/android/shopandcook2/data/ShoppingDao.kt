package com.johnmagdalinos.android.shopandcook2.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ShoppingDao {
    // Returns a list of all shopping entries
    @Query("SELECT * FROM shopping ORDER BY checked ASC, name ASC")
    fun getAllShoppingASCName(): LiveData<List<ShoppingEntry>>

    @Query("SELECT * FROM shopping ORDER BY checked ASC, name DESC")
    fun getAllShoppingDESCName(): LiveData<List<ShoppingEntry>>

    @Query("SELECT * FROM shopping ORDER BY checked ASC, color ASC, name ASC")
    fun getAllShoppingASCColor(): LiveData<List<ShoppingEntry>>

    @Query("SELECT * FROM shopping ORDER BY checked ASC, color DESC, name ASC")
    fun getAllShoppingDESCColor(): LiveData<List<ShoppingEntry>>

    // Inserts a shopping entry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingEntry(entry: ShoppingEntry)

    // Inserts multiple entries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultipleShoppingEntries(vararg entries: ShoppingEntry)

    // Updates a shopping entry
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShoppingEntry(entry: ShoppingEntry)

    // Deletes all shopping entries
    @Query("DELETE FROM shopping ")
    fun deleteAllShopping()

    // Deleties a single shopping entry
    @Delete
    fun deleteShoppingEntry(entry: ShoppingEntry)
}