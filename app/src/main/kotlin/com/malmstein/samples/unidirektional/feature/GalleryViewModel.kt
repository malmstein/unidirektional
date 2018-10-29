package com.malmstein.samples.unidirektional.feature

import android.arch.lifecycle.ViewModel
import com.malmstein.samples.unidirektional.store.ViewStateStore

class GalleryViewModel(val getPhotos: GetPhotos) : ViewModel() {

    val store = ViewStateStore(GalleryViewState())

    fun loadGallery() {
        store.dispatchActions(getPhotos())
    }

}
