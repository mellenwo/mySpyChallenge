package xyz.blueowl.ispychallenge.ui.near_me

import android.location.Location

class LocationDelegateImpl: LocationDelegate {
    override fun calculateDistanceBetween(
        startLat: Double,
        startLong: Double,
        endLat: Double,
        endLong: Double
    ): Double {
        val results = floatArrayOf(0f)

        Location.distanceBetween(
            startLat,
            startLong,
            endLat,
            endLong,
            results
        )

        return results[0].toDouble()
    }
}