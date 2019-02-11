package com.malmstein.samples.unidirektional.store

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

open class MainUseCase {

    suspend fun <T> ProducerScope<State<T>>.send(f: T.() -> T) = send(State(f))

    fun <T> produceActions(f: suspend ProducerScope<State<T>>.() -> Unit): ReceiveChannel<State<T>> =
        GlobalScope.produce(block = f)

}
