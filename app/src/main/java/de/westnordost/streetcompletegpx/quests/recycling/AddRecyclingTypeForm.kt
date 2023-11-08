package de.westnordost.streetcompletegpx.quests.recycling

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddRecyclingTypeForm : AImageListQuestForm<RecyclingType, RecyclingType>() {

    override val items = RecyclingType.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<RecyclingType>) {
        applyAnswer(selectedItems.single())
    }
}
