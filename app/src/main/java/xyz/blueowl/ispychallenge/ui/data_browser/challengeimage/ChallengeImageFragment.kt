package xyz.blueowl.ispychallenge.ui.data_browser.challengeimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import xyz.blueowl.ispychallenge.databinding.FragmentChallengeImageBinding

class ChallengeImageFragment: Fragment() {

    private var _binding: FragmentChallengeImageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageUrl = arguments?.let {
            val params = ChallengeImageFragmentArgs.fromBundle(it)
            params.challengeImageUrl
        } ?: throw IllegalArgumentException("Missing image url")

        _binding = FragmentChallengeImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Picasso.get()
            .load(imageUrl)
            .into(binding.challengeImg)

        return root
    }
}