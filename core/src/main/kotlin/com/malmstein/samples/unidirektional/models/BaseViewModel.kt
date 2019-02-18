package com.malmstein.samples.unidirektional.models

import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.malmstein.samples.unidirektional.store.ViewStateStore

open class BaseViewModel: ViewModel() {

    val store: ViewStateStore = ViewStateStore()

    @VisibleForTesting
    val eventLiveData = MutableLiveData<ViewEvent>()

    fun observeEvent(owner: LifecycleOwner, observer: (ViewEvent) -> Unit) =
            eventLiveData.observe(owner, Observer { observer(it!!) })

    @MainThread
    fun dispatchEvent(state: ViewEvent) {
        eventLiveData.value = state
    }

}