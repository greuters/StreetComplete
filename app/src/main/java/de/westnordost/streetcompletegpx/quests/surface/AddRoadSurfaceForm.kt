package de.westnordost.streetcompletegpx.quests.surface

import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.osm.surface.SELECTABLE_WAY_SURFACES
import de.westnordost.streetcompletegpx.osm.surface.Surface
import de.westnordost.streetcompletegpx.osm.surface.SurfaceAndNote
import de.westnordost.streetcompletegpx.osm.surface.isSurfaceAndTracktypeConflicting
import de.westnordost.streetcompletegpx.osm.surface.toItems
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddRoadSurfaceForm : AImageListQuestForm<Surface, SurfaceAndNote>() {
    override val items get() = SELECTABLE_WAY_SURFACES.toItems()

    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<Surface>) {
        val surface = selectedItems.single()
        confirmPotentialTracktypeMismatch(surface) {
            collectSurfaceDescriptionIfNecessary(requireContext(), surface) { description ->
                applyAnswer(SurfaceAndNote(surface, description))
            }
        }
    }

    private fun confirmPotentialTracktypeMismatch(surface: Surface, onConfirmed: () -> Unit) {
        val tracktype = element.tags["tracktype"]
        if (isSurfaceAndTracktypeConflicting(surface.osmValue!!, tracktype)) {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.quest_generic_confirmation_title)
                .setMessage(R.string.quest_surface_tractypeMismatchInput_confirmation_description)
                .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ ->
                    onConfirmed()
                }
                .setNegativeButton(R.string.quest_generic_confirmation_no, null)
                .show()
        } else {
            onConfirmed()
        }
    }
}
