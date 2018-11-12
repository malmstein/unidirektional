package com.malmstein.samples.unidirektional.store

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

open class MainUseCase {

    suspend fun <T> ProducerScope<Action<T>>.send(f: T.() -> T) = send(Action(f))

    fun <T> produceActions(f: suspend ProducerScope<Action<T>>.() -> Unit): ReceiveChannel<Action<T>> = GlobalScope.produce(block = f)

}
