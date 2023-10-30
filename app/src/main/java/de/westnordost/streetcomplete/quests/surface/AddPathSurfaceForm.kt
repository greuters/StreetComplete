package de.westnordost.streetcompletegpx.quests.surface

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.mapdata.Way
import de.westnordost.streetcompletegpx.osm.surface.SELECTABLE_WAY_SURFACES
import de.westnordost.streetcompletegpx.osm.surface.Surface
import de.westnordost.streetcompletegpx.osm.surface.SurfaceAndNote
import de.westnordost.streetcompletegpx.osm.surface.toItems
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.util.ktx.couldBeSteps

class AddPathSurfaceForm : AImageListQuestForm<Surface, SurfaceOrIsStepsAnswer>() {
    override val items get() = SELECTABLE_WAY_SURFACES.toItems()

    override val otherAnswers get() = listOfNotNull(
        createConvertToStepsAnswer(),
        createMarkAsIndoorsAnswer(),
    )

    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<Surface>) {
        val value = selectedItems.single()
        collectSurfaceDescriptionIfNecessary(requireContext(), value) {
            applyAnswer(SurfaceAnswer(SurfaceAndNote(value, it)))
        }
    }

    private fun createConvertToStepsAnswer(): AnswerItem? {
        return if (element.couldBeSteps()) {
            AnswerItem(R.string.quest_generic_answer_is_actually_steps) {
                applyAnswer(IsActuallyStepsAnswer)
            }
        } else null
    }

    private fun createMarkAsIndoorsAnswer(): AnswerItem? {
        val way = element as? Way ?: return null
        if (way.tags["indoor"] == "yes") return null

        return AnswerItem(R.string.quest_generic_answer_is_indoors) {
            applyAnswer(IsIndoorsAnswer)
        }
    }
}
