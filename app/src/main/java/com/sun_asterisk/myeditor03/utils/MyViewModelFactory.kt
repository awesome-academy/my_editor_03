package com.sun_asterisk.myeditor03.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.screen.collections.CollectionViewModel

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val photoRepository: PhotoRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CollectionViewModel::class.java -> CollectionViewModel(photoRepository) as T
            else -> super.create(modelClass)
        }
    }
}
