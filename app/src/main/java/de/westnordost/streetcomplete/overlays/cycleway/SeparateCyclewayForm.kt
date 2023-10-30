package de.westnordost.streetcompletegpx.overlays.cycleway

import android.os.Bundle
import android.view.View
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapChangesBuilder
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.UpdateElementTagsAction
import de.westnordost.streetcompletegpx.osm.cycleway_separate.SeparateCycleway
import de.westnordost.streetcompletegpx.osm.cycleway_separate.SeparateCycleway.*
import de.westnordost.streetcompletegpx.osm.cycleway_separate.applyTo
import de.westnordost.streetcompletegpx.osm.cycleway_separate.asItem
import de.westnordost.streetcompletegpx.osm.cycleway_separate.createSeparateCycleway
import de.westnordost.streetcompletegpx.overlays.AImageSelectOverlayForm
import de.westnordost.streetcompletegpx.view.image_select.DisplayItem

class SeparateCyclewayForm : AImageSelectOverlayForm<SeparateCycleway>() {

    override val items: List<DisplayItem<SeparateCycleway>> get() =
        listOf(PATH, NON_DESIGNATED, NON_SEGREGATED, SEGREGATED, EXCLUSIVE_WITH_SIDEWALK, EXCLUSIVE).map {
            it.asItem(countryInfo.isLeftHandTraffic)
        }

    override val itemsPerRow = 1
    override val cellLayoutId = R.layout.cell_labeled_icon_select_right

    private var currentCycleway: SeparateCycleway? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cycleway = createSeparateCycleway(element!!.tags)

        /* Not displaying bicycle=yes and bicycle=no on footways and treating it the same because
           whether riding a bike on a footway is allowed by default (without requiring signs) or
           only under certain conditions (e.g. certain minimum width of sidewalk) is very much
           dependent on the country or state one is in.

           Hence, it is not verifiable well for the on-site surveyor: If there is no sign that
           specifically allows or forbids cycling on a footway, the user is left with his loose
           (mis)understanding of the local legislation to decide. After all, bicycle=yes/no
           is (usually) nothing physical, but merely describes what is legal. It is in that sense
           then not information surveyable on-the-ground, unless specifically signed.
           bicycle=yes/no does however not make a statement about from where this info is derived.

           So, from an on-site surveyor point of view, it is always better to record what is signed,
           instead of what follows from that signage.

           Signage, however, is out of scope of this overlay because while the physical presence of
           a cycleway can be validated at a glance, the presence of a sign requires to walk a bit up
           or down the street in order to find (or not find) a sign.
           More importantly, at the time of writing, there is no way to tag the information that a
           bicycle=* access restriction is derived from the presence of a sign. This however is a
           prerequisite for it being  displayed as a selectable option due to the reasons stated
           above.
        */
        currentCycleway = if (cycleway == NOT_ALLOWED || cycleway == ALLOWED_ON_FOOTWAY) NON_DESIGNATED else cycleway
        selectedItem = currentCycleway?.asItem(countryInfo.isLeftHandTraffic)
    }

    override fun hasChanges(): Boolean =
        selectedItem?.value != currentCycleway

    override fun onClickOk() {
        val tagChanges = StringMapChangesBuilder(element!!.tags)
        selectedItem!!.value!!.applyTo(tagChanges)
        applyEdit(UpdateElementTagsAction(element!!, tagChanges.create()))
    }
}
