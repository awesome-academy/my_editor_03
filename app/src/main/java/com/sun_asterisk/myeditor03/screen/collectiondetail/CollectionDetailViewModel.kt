package com.sun_asterisk.myeditor03.screen.collectiondetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CollectionDetailViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    var photosByCollectionLiveData = MutableLiveData<List<Photo>>()
    private val compositeDisposable = CompositeDisposable()

    fun getPhotosByCollection(id: String, page: Int) {
        compositeDisposable.add(
            photoRepository.getPhotosByCollection(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data -> data.let { photosByCollectionLiveData.value = it } }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.i("onError", error.message)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
