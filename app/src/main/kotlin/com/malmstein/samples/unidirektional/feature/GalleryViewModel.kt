package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.BaseViewModel
import com.malmstein.samples.unidirektional.store.ViewStateStore

class GalleryViewModel(val getPhotos: GetPhotos,
                       val getPhoto: GetPhoto) : BaseViewModel() {

    val store = ViewStateStore()

    fun loadGallery() {
        store.dispatchStates(getPhoto())
    }

}
