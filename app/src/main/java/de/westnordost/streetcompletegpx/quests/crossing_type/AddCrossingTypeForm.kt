package de.westnordost.streetcompletegpx.quests.crossing_type

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddCrossingTypeForm : AImageListQuestForm<CrossingType, CrossingType>() {

    override val items = CrossingType.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<CrossingType>) {
        applyAnswer(selectedItems.single())
    }
}
