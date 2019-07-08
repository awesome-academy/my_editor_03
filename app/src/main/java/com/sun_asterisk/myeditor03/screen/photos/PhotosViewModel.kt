package com.sun_asterisk.myeditor03.screen.photos

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotosViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    var photoLiveData = MutableLiveData<List<Photo>>()
    private val compositeDisposable = CompositeDisposable()

    fun getPhotos(page: Int) {
        compositeDisposable.add(
            photoRepository.getPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoLiveData.value = it }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.i("onError", error.message)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
