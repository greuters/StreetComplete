package de.westnordost.streetcompletegpx.quests.seating

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.seating.Seating.INDOOR_AND_OUTDOOR
import de.westnordost.streetcompletegpx.quests.seating.Seating.NO
import de.westnordost.streetcompletegpx.quests.seating.Seating.ONLY_INDOOR
import de.westnordost.streetcompletegpx.quests.seating.Seating.ONLY_OUTDOOR

class AddSeatingForm : AListQuestForm<Seating>() {
    override val items = listOf(
        TextItem(INDOOR_AND_OUTDOOR, R.string.quest_seating_indoor_and_outdoor),
        TextItem(ONLY_INDOOR, R.string.quest_seating_indoor_only),
        TextItem(ONLY_OUTDOOR, R.string.quest_seating_outdoor_only),
        TextItem(NO, R.string.quest_seating_takeaway),
    )
}
