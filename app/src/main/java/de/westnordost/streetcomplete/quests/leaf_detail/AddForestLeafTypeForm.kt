package de.westnordost.streetcompletegpx.quests.leaf_detail

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddForestLeafTypeForm : AImageListQuestForm<ForestLeafType, ForestLeafType>() {

    override val items = ForestLeafType.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<ForestLeafType>) {
        applyAnswer(selectedItems.single())
    }
}
