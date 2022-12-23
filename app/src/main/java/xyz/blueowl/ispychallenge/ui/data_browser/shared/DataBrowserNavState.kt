package xyz.blueowl.ispychallenge.ui.data_browser.shared

/**
 * State classes related to the navigation states of UI in the DataBrowser UI Package.
 *
 * It is not necessary to utilize this in the Tech Challenge.
 */
sealed class DataBrowserNavState {

    class UserNavState(val userId: String): DataBrowserNavState()
    class ChallengesNavState(val userId: String): DataBrowserNavState()
    class ChallengeNavState(val challengeId: String): DataBrowserNavState()
    class ChallengeImageNavState(val challengeImageId: String): DataBrowserNavState()
    class MatchesNavState(val userId: String?, val challengeId: String?): DataBrowserNavState()
    class MatchNavState(val matchId: String): DataBrowserNavState()
    class RatingsNavState(val userId: String?, val challengeId: String?): DataBrowserNavState()
}
