package de.westnordost.streetcompletegpx.quests.bike_shop

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.bike_shop.SecondHandBicycleAvailability.NEW_AND_SECOND_HAND
import de.westnordost.streetcompletegpx.quests.bike_shop.SecondHandBicycleAvailability.NO_BICYCLES_SOLD
import de.westnordost.streetcompletegpx.quests.bike_shop.SecondHandBicycleAvailability.ONLY_NEW
import de.westnordost.streetcompletegpx.quests.bike_shop.SecondHandBicycleAvailability.ONLY_SECOND_HAND

class AddSecondHandBicycleAvailabilityForm : AListQuestForm<SecondHandBicycleAvailability>() {

    override val items = listOf(
        TextItem(ONLY_NEW, R.string.quest_bicycle_shop_second_hand_only_new),
        TextItem(NEW_AND_SECOND_HAND, R.string.quest_bicycle_shop_second_hand_new_and_used),
        TextItem(ONLY_SECOND_HAND, R.string.quest_bicycle_shop_second_hand_only_used),
        TextItem(NO_BICYCLES_SOLD, R.string.quest_bicycle_shop_second_hand_no_bicycles),
    )
}
