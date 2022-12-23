package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemChallengeBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem

data class ChallengeListItem(
    val hintText: String,
    val winsText: String,
    val starsText: String,
    val distanceText: String,
    val createdByText: String,
    val distance: Double,
    val onClick: () -> Unit
) : AdapterItem() {

    override val id: Any = ChallengeListItem::javaClass
    override val layoutResource: Int = R.layout.item_challenge

    override fun bind(view: View) {
        ItemChallengeBinding.bind(view).apply {
            this.textViewCreatedBy.text = createdByText
            this.textViewDistance.text = distanceText
            this.textViewStars.text = starsText
            this.textViewHint.text = hintText
            this.textViewWins.text = winsText
        }

        view.setOnClickListener { onClick() }
    }

}