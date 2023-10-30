package de.westnordost.streetcompletegpx.quests.railway_crossing

import de.westnordost.streetcompletegpx.quests.AImageListQuestForm
import de.westnordost.streetcompletegpx.view.image_select.DisplayItem

class AddRailwayCrossingBarrierForm : AImageListQuestForm<RailwayCrossingBarrier, RailwayCrossingBarrier>() {

    override val items: List<DisplayItem<RailwayCrossingBarrier>> get() {
        val isPedestrian = element.tags["railway"] == "crossing"
        return RailwayCrossingBarrier.getSelectableValues(isPedestrian)
            .map { it.asItem(countryInfo.isLeftHandTraffic) }
    }

    override val itemsPerRow = 4

    override fun onClickOk(selectedItems: List<RailwayCrossingBarrier>) {
        applyAnswer(selectedItems.single())
    }
}
