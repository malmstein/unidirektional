package com.malmstein.samples.unidirektional.store

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.ViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class State<T>(private val f: T.() -> T) {
    operator fun invoke(t: T) = t.f()
}

class ViewStateStore(initialState: Either<ViewState, Failure> = Either.Left(ViewState.Idle)) : CoroutineScope {

    private val job = Job()

    private val stateLiveData = MutableLiveData<Either<ViewState, Failure>>()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun observeState(owner: LifecycleOwner, observer: (Either<ViewState, Failure>) -> Unit) =
        stateLiveData.observe(owner, Observer { observer(it!!) })

    @MainThread
    fun dispatchState(state: Either<ViewState, Failure>) {
        stateLiveData.value = state
    }

    fun dispatchState(f: suspend (Either<ViewState, Failure>) -> State<Either<ViewState, Failure>>) {
        GlobalScope.launch {
            val action = f(state())
            withContext(Dispatchers.Main) {
                dispatchState(action(state()))
            }
        }
    }

    fun dispatchStates(channel: ReceiveChannel<State<Either<ViewState, Failure>>>) {
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
