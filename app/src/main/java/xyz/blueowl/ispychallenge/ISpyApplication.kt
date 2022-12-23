package xyz.blueowl.ispychallenge

import android.app.Application
import xyz.blueowl.ispychallenge.api.service.APIService
import xyz.blueowl.ispychallenge.api.service.MockAPIService
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.data.repository.LocalDataRepository
import xyz.blueowl.ispychallenge.ui.near_me.LocationDelegate
import xyz.blueowl.ispychallenge.ui.near_me.LocationDelegateImpl
import java.io.InputStreamReader

class ISpyApplication: Application() {

    lateinit var apiService: APIService

    lateinit var dataRepository: DataRepository

    lateinit var locationDelegate: LocationDelegate

    override fun onCreate() {
        super.onCreate()

        apiService = MockAPIService(
            InputStreamReader(assets.open("users.json")),
            InputStreamReader(assets.open("challenges.json")))

        locationDelegate = LocationDelegateImpl()

        dataRepository = LocalDataRepository(apiService)
    }
}
