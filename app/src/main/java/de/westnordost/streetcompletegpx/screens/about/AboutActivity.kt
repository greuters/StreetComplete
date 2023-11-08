package de.westnordost.streetcompletegpx.screens.about

import android.os.Bundle
import de.westnordost.streetcompletegpx.screens.FragmentContainerActivity

class AboutActivity : FragmentContainerActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (savedInstanceState == null) {
            replaceMainFragment(TwoPaneAboutFragment())
        }
    }
}
