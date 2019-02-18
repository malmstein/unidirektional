package com.malmstein.samples.unidirektional.models

import androidx.lifecycle.ViewModel
import com.malmstein.samples.unidirektional.store.ViewStateStore

open class BaseViewModel : ViewModel() {

    val store: ViewStateStore = ViewStateStore()

}
