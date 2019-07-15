package com.sun_asterisk.myeditor03.screen.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    val searHistoryLiveData = MutableLiveData<List<Search>>()
    var errorLiveData = MutableLiveData<Throwable>()
    private val compositeDisposable = CompositeDisposable()

    fun getSearchHistory() {
        compositeDisposable.add(
            photoRepository.getAllHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let { searHistoryLiveData.value = it }
                }, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }

    fun addSearchHistory(search: Search) {
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<Any> { e ->
                photoRepository.insertHistory(search)
                e.onComplete()
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }

    fun removeSearchHistory(search: Search) {
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<Any> { e ->
                photoRepository.deleteHistory(search)
                e.onComplete()
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error ->
                    error.let { errorLiveData.value = it }
                })
        )
    }
}
