package com.malmstein.samples.unidirektional.store

sealed class ViewEvent {
    data class Message(val message: String) : ViewEvent()
    object Close : ViewEvent()

    // Feature specific View Events
    open class ChangeOrderAddress : ViewEvent()

    open class CancelCard : ViewEvent()
}
