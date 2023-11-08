package de.westnordost.streetcompletegpx.quests.surface

import de.westnordost.streetcompletegpx.osm.surface.SELECTABLE_WAY_SURFACES
import de.westnordost.streetcompletegpx.osm.surface.Surface
import de.westnordost.streetcompletegpx.osm.surface.SurfaceAndNote
import de.westnordost.streetcompletegpx.osm.surface.toItems
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddPathPartSurfaceForm : AImageListQuestForm<Surface, SurfaceAndNote>() {
    override val items get() = SELECTABLE_WAY_SURFACES.toItems()

    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<Surface>) {
        val value = selectedItems.single()

        collectSurfaceDescriptionIfNecessary(requireContext(), value) {
            applyAnswer(SurfaceAndNote(value, it))
        }
    }
}
