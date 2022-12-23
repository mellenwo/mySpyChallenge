package xyz.blueowl.ispychallenge.ui.near_me

import xyz.blueowl.ispychallenge.data.models.Challenge
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.ChallengeListItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import kotlin.math.roundToInt

class NearMeViewModel(
    val dataRepository: DataRepository,
    val locationDelegate: LocationDelegate
    ) : BaseDataBrowserViewModel() {

    fun onLocationChanged(latitude: Double, longitude: Double) {
        val adapterItems = mutableListOf<ChallengeListItem>()

        dataRepository.getAllChallenges()
            .forEach { challenge ->
                adapterItems.add(challenge.toChallengeListItem(
                    latitude,
                    longitude
                ))
            }

        adapterItems.sortBy { challengeListItem ->
            challengeListItem.distance
        }

        _adapterItems.tryEmit(adapterItems)
    }

    private fun onChallengeItemClicked(challengeUrl: String) {
        _navigationFlow.tryEmit(DataBrowserNavState.ChallengeImageNavState(challengeUrl))
    }

    private fun Challenge.toChallengeListItem(latitude: Double, longitude: Double): ChallengeListItem {
        val calculatedDistance = locationDelegate.calculateDistanceBetween(
            latitude,
            longitude,
            this.latitude,
            this.longitude
        )
        return ChallengeListItem(
            hintText = hint,
            winsText = "${matches.filter { it.verified }.size} wins",
            starsText = "${(ratings.map { it.stars }.average() * 100).roundToInt() / 100.0} stars",
            distanceText = "${(calculatedDistance * 100).roundToInt() / 100.0} m",
            createdByText = "By: ${dataRepository.getUserByUserId(this.userId)?.username ?: "unknown"}",
            distance = calculatedDistance
        ) {
            onChallengeItemClicked(photoImageName)
        }
    }
}