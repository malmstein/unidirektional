package com.malmstein.samples.unidirektional

import com.malmstein.samples.unidirektional.store.Action
import io.mockk.ConstantAnswer
import io.mockk.MockKStubScope
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.fold
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner



/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {

    @Suppress("LeakingThis")
    @Rule @JvmField val injectMocks = InjectMocksRule.create(this@UnitTest)
    infix fun <T, B> MockKStubScope<Deferred<T>, B>.returns(returnValue: T) = answers(ConstantAnswer(async { returnValue }))

    suspend inline fun <T> ReceiveChannel<Action<T>>.states(initialState: T): List<T> {
        return fold(emptyList()) { states, action ->
            states + action(states.lastOrNull() ?: initialState)
        }
    }
}

abstract class MainUseCaseTest {
    infix fun <T, B> MockKStubScope<Deferred<T>, B>.returns(returnValue: T) = answers(ConstantAnswer(async { returnValue }))

    suspend inline fun <T> ReceiveChannel<Action<T>>.states(initialState: T): List<T> {
        return fold(emptyList()) { states, action ->
            states + action(states.lastOrNull() ?: initialState)
        }
    }
}

class InjectMocksRule {

    companion object {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockitoAnnotations.initMocks(testClass)
            statement
        }
    }
}
