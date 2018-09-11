package com.johnmagdalinos.android.shopandcook2.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.johnmagdalinos.android.shopandcook2.data.Repository

class DetailViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}