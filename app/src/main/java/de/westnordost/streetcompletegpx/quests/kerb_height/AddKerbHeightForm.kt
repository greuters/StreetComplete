package de.westnordost.streetcompletegpx.quests.kerb_height

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddKerbHeightForm : AImageListQuestForm<KerbHeight, KerbHeight>() {

    override val items = KerbHeight.values().map { it.asItem() }
    override val itemsPerRow = 2
    override val moveFavoritesToFront = false

    override fun onClickOk(selectedItems: List<KerbHeight>) {
        applyAnswer(selectedItems.single())
    }
}
