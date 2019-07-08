package com.sun_asterisk.myeditor03.screen.collections

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CollectionViewModel (private val photoRepository: PhotoRepository): ViewModel() {
    var collectionsLiveData = MutableLiveData<List<Collection>>()
    private val compositeDisposable = CompositeDisposable()

    fun getCollections(page: Int) {
        compositeDisposable.add(
            photoRepository.getCollections(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ collectionsLiveData.value = it }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.i("onError", error.message)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
