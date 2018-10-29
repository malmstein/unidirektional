package com.malmstein.samples.unidirektional.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.malmstein.samples.unidirektional.extensions.isVisible
import kotlinx.android.synthetic.main.activity_gallery.*
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity() {

    val galleryViewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

    }

    private fun initViewModel() {
        galleryViewModel.store.observe(this) {
            gallery_loading.isVisible = it.loading
        }
        galleryViewModel.loadGallery()
    }

}
