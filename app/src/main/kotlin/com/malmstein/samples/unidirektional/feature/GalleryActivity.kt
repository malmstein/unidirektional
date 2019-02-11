package com.malmstein.samples.unidirektional.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity() {

    val galleryViewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

    }

    private fun initViewModel() {
        galleryViewModel.store.observeState(this) {
            when (it) {
                is Either.Left -> renderSuccess(it.a)
                is Either.Right -> renderFailure(it.b)
            }
        }
        galleryViewModel.loadGallery()
    }

    private fun renderSuccess(state: ViewState) {

    }

    private fun renderFailure(failure: Failure) {

    }


}
