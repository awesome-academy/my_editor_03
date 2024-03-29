package com.sun_asterisk.myeditor03.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.screen.collectiondetail.CollectionDetailViewModel
import com.sun_asterisk.myeditor03.screen.collections.CollectionViewModel
import com.sun_asterisk.myeditor03.screen.photodetail.PhotoDetailViewModel
import com.sun_asterisk.myeditor03.screen.photos.PhotosViewModel
import com.sun_asterisk.myeditor03.screen.search.SearchViewModel

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val photoRepository: PhotoRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CollectionViewModel::class.java -> CollectionViewModel(photoRepository) as T
            PhotosViewModel::class.java -> PhotosViewModel(photoRepository) as T
            CollectionDetailViewModel::class.java -> CollectionDetailViewModel(photoRepository) as T
            PhotoDetailViewModel::class.java -> PhotoDetailViewModel(photoRepository) as T
            SearchViewModel::class.java -> SearchViewModel(photoRepository) as T
            else -> super.create(modelClass)
        }
    }
}
