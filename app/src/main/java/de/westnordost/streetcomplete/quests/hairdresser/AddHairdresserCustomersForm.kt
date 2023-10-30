package de.westnordost.streetcompletegpx.quests.hairdresser

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.hairdresser.HairdresserCustomers.MALE_AND_FEMALE
import de.westnordost.streetcompletegpx.quests.hairdresser.HairdresserCustomers.NOT_SIGNED
import de.westnordost.streetcompletegpx.quests.hairdresser.HairdresserCustomers.ONLY_FEMALE
import de.westnordost.streetcompletegpx.quests.hairdresser.HairdresserCustomers.ONLY_MALE

class AddHairdresserCustomersForm : AListQuestForm<HairdresserCustomers>() {
    override val items = listOf(
        TextItem(MALE_AND_FEMALE, R.string.quest_hairdresser_male_and_female),
        TextItem(ONLY_FEMALE, R.string.quest_hairdresser_female_only),
        TextItem(ONLY_MALE, R.string.quest_hairdresser_male_only),
        TextItem(NOT_SIGNED, R.string.quest_hairdresser_not_signed),
    )
}
