package de.westnordost.streetcompletegpx.quests.parking_type

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddParkingTypeForm : AImageListQuestForm<ParkingType, ParkingType>() {

    override val items = ParkingType.values().map { it.asItem() }
    override val itemsPerRow = 3

    override fun onClickOk(selectedItems: List<ParkingType>) {
        applyAnswer(selectedItems.single())
    }
}
