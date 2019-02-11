package com.malmstein.samples.unidirektional.store

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext


class State<T>(private val f: T.() -> T) {
    operator fun invoke(t: T) = t.f()
}

class ViewStateStore<T : Any>(initialState: T) : CoroutineScope {

    private val job = Job()

    private val stateLiveData = MutableLiveData<T>().apply {
        value = initialState
    }

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun observeState(owner: LifecycleOwner, observer: (T) -> Unit) =
        stateLiveData.observe(owner, Observer { observer(it!!) })

    @MainThread
    fun dispatchState(state: T) {
        stateLiveData.value = state
    }

    fun dispatchState(f: suspend (T) -> State<T>) {
        GlobalScope.launch {
            val action = f(state())
            withContext(Dispatchers.Main) {
                dispatchState(action(state()))
            }
        }
    }

    fun dispatchStates(channel: ReceiveChannel<State<T>>) {
        launch {
            channel.consumeEach { action ->
                withContext(Dispatchers.Main) {
                    dispatchState(action(state()))
                }
            }
        }
    }

    fun state() = stateLiveData.value!!

    fun cancel() = job.cancel()

}
