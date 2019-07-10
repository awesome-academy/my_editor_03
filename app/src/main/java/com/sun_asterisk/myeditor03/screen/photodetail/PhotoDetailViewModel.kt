package com.sun_asterisk.myeditor03.screen.photodetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoDetailViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    var photoDetailLiveData = MutableLiveData<Photo>()
    var errorLiveData = MutableLiveData<Throwable>()
    private val compositeDisposable = CompositeDisposable()

    fun getPhotoDetail(id: String) {
        compositeDisposable.add(
            photoRepository.getPhotoDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let { photoDetailLiveData.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
