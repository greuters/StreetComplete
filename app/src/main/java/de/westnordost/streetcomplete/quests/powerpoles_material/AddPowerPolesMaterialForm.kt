package de.westnordost.streetcompletegpx.quests.powerpoles_material

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddPowerPolesMaterialForm : AImageListQuestForm<PowerPolesMaterial, PowerPolesMaterial>() {

    override val items = PowerPolesMaterial.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<PowerPolesMaterial>) {
        applyAnswer(selectedItems.single())
    }
}
