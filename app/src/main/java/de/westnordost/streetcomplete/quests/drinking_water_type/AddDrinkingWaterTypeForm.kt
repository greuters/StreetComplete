package de.westnordost.streetcompletegpx.quests.drinking_water_type

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddDrinkingWaterTypeForm : AImageListQuestForm<DrinkingWaterType, DrinkingWaterType>() {

    override val items = DrinkingWaterType.values().map { it.asItem() }

    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<DrinkingWaterType>) {
        applyAnswer(selectedItems.single())
    }
}
