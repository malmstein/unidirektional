package com.malmstein.samples.unidirektional.store

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.channels.ProducerScope
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce

open class MainUseCase {

    suspend fun <T> ProducerScope<Action<T>>.send(f: T.() -> T) = send(Action(f))

    fun <T> produceActions(f: suspend ProducerScope<Action<T>>.() -> Unit): ReceiveChannel<Action<T>> = GlobalScope.produce(block = f)

}
