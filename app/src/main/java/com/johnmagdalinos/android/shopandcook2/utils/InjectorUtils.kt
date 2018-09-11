package com.johnmagdalinos.android.shopandcook2.utils

import android.content.Context
import com.johnmagdalinos.android.shopandcook2.AppExecutors
import com.johnmagdalinos.android.shopandcook2.data.Repository
import com.johnmagdalinos.android.shopandcook2.data.ShoppingDatabase
import com.johnmagdalinos.android.shopandcook2.viewmodels.DetailViewModelFactory

class InjectorUtils {

    fun provideDetailViewModelFactory(context: Context) : DetailViewModelFactory {
        val repository = provideRepository(context)
        return DetailViewModelFactory(repository)
    }

    fun provideRepository(context: Context) : Repository {
        val shoppingDatabase = ShoppingDatabase.newInstance(context)
        val appExecutors = AppExecutors.getInstance()
        return Repository.newInstance(shoppingDatabase.shoppingDao(), appExecutors)
    }

}