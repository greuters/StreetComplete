package de.westnordost.streetcompletegpx.quests.barrier_type

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddBarrierTypeForm : AImageListQuestForm<BarrierType, BarrierType>() {

    override val items = BarrierType.values().map { it.asItem() }

    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<BarrierType>) {
        applyAnswer(selectedItems.single())
    }
}
