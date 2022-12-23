package xyz.blueowl.ispychallenge.ui.near_me

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import xyz.blueowl.ispychallenge.databinding.FragmentNearMeBinding
import xyz.blueowl.ispychallenge.extensions.requireISpyApplication
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.shared.GenericSingleViewModelFactory
import xyz.blueowl.ispychallenge.ui.data_browser.shared.UniversalListAdapter
import xyz.blueowl.ispychallenge.ui.safeCollectFlow

class NearMeFragment : Fragment() {

    private var _binding: FragmentNearMeBinding? = null
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var viewModel: NearMeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = GenericSingleViewModelFactory (NearMeViewModel::class.java) {
            NearMeViewModel(
                requireISpyApplication().dataRepository,
                requireISpyApplication().locationDelegate
            )
        }
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel =
            ViewModelProvider(this, factory)[NearMeViewModel::class.java]

        _binding = FragmentNearMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nearMeAdapter = UniversalListAdapter()
        binding.recyclerViewNearMeChallenges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nearMeAdapter
        }

        requestLocation()

        safeCollectFlow(viewModel.adapterItems) {
            nearMeAdapter.submitList(it)
        }

        safeCollectFlow(viewModel.navigationFlow) { navState ->
            val action = when (navState) {
                is DataBrowserNavState.ChallengeImageNavState -> {
                  NearMeFragmentDirections.actionNavigationNearMeToChallengeImage(navState.challengeImageId)
                }
                else -> throw IllegalArgumentException("Cannot support nav state $navState")
            }

            findNavController().navigate(action)
        }

        return root
    }

    private fun requestLocation() {
        val requestPermissionsLauncher =
            registerForActivityResult(RequestPermission()) { isGranted ->
                if (isGranted) {
                    getLastKnownLocation()
                } else {
                    // want provide user with explanation why we need it
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation()
            }
            else -> {
                requestPermissionsLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun getLastKnownLocation() {
        fusedLocationProvider.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.onLocationChanged(
                        it.latitude,
                        it.longitude
                    )
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}