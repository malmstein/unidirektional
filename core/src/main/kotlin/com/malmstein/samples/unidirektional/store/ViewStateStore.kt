package com.malmstein.samples.unidirektional.store

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlin.coroutines.experimental.CoroutineContext

class Action<T>(private val f: T.() -> T) {
    operator fun invoke(t: T) = t.f()
}

class ViewStateStore<T : Any>(initialState: T) : CoroutineScope {

    private val liveData = MutableLiveData<T>().apply {
        value = initialState
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) =
        liveData.observe(owner, Observer { observer(it!!) })

    @MainThread
    fun dispatchState(state: T) {
        liveData.value = state
    }

    fun dispatchAction(f: suspend (T) -> Action<T>) {
        launch {
            val action = f(state())
            withContext(Dispatchers.Main) {
                dispatchState(action(state()))
            }
        }
    }

    fun dispatchActions(channel: ReceiveChannel<Action<T>>) {
        launch {
            channel.consumeEach { action ->
                withContext(Dispatchers.Main) {
                    dispatchState(action(state()))
                }
            }
        }
    }

    fun state() = liveData.value!!

    fun cancel() = job.cancel()

}
