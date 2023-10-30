package de.westnordost.streetcompletegpx.quests.memorial_type

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddMemorialTypeForm : AImageListQuestForm<MemorialType, MemorialType>() {

    override val items = MemorialType.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<MemorialType>) {
        applyAnswer(selectedItems.single())
    }
}
