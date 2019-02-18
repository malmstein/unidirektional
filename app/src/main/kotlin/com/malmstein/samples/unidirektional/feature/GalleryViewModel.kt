package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.models.BaseViewModel

class GalleryViewModel(val getPhotos: GetPhotos) : BaseViewModel() {

    fun loadGallery() {
        store.dispatchStates(getPhotos())
    }

}
