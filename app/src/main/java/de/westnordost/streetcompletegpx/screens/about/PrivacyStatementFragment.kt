package de.westnordost.streetcompletegpx.screens.about

import android.os.Bundle
import android.view.View
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.FragmentShowHtmlBinding
import de.westnordost.streetcompletegpx.screens.HasTitle
import de.westnordost.streetcompletegpx.screens.TwoPaneDetailFragment
import de.westnordost.streetcompletegpx.screens.main.map.VectorTileProvider
import de.westnordost.streetcompletegpx.util.viewBinding
import de.westnordost.streetcompletegpx.view.setHtml
import org.koin.android.ext.android.inject

/** Shows the privacy statement */
class PrivacyStatementFragment : TwoPaneDetailFragment(R.layout.fragment_show_html), HasTitle {

    private val vectorTileProvider: VectorTileProvider by inject()

    private val binding by viewBinding(FragmentShowHtmlBinding::bind)

    override val title: String get() = getString(R.string.about_title_privacy_statement)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.setHtml(
            getString(R.string.privacy_html) +
            getString(R.string.privacy_html_tileserver2, vectorTileProvider.title, vectorTileProvider.privacyStatementLink) +
            getString(R.string.privacy_html_statistics) +
            getString(R.string.privacy_html_third_party_quest_sources) +
            getString(R.string.privacy_html_image_upload2))
    }
}
