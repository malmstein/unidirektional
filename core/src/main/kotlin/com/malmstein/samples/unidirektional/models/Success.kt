package com.malmstein.samples.unidirektional.models

/**
 * Base Class for handling success/viewstates/navigation events
 * Every feature specific success should extend [FeatureSuccess] class.
 */
sealed class Success {
    object Idle : Success()
    object Loading : Success()

    /** * Extend this class for feature specific success.*/
    abstract class FeatureSuccess : Success()
}
