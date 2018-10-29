package com.malmstein.samples.unidirektional.feature

data class GalleryViewState(
        val photos: List<Photo> = emptyList(),
        val loading: Boolean = false,
        val error: Throwable? = null
)
