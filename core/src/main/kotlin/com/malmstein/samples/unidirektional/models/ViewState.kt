package com.malmstein.samples.unidirektional.models

sealed class ViewState {
    object Idle: ViewState()
    object Loading: ViewState()
    data class GeneralError(val exception: Exception) : ViewState()

    // Feature specific View States
    open class Photos: ViewState()
}
