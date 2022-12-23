package xyz.blueowl.ispychallenge.ui.near_me

interface LocationDelegate {
    fun calculateDistanceBetween(
        startLat: Double,
        startLong: Double,
        endLat: Double,
        endLong: Double
    ): Double
}