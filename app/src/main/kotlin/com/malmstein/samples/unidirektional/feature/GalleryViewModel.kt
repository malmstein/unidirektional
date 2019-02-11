package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.store.BaseViewModel
import com.malmstein.samples.unidirektional.store.ViewStateStore

class GalleryViewModel(val getPhotos: GetPhotos) : BaseViewModel() {

    val store = ViewStateStore(GalleryViewState())

    fun loadGallery() {
        store.dispatchStates(getPhotos())
    }

}
