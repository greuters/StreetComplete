package de.westnordost.streetcompletegpx.quests.camping

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.camping.CampType.BACKCOUNTRY
import de.westnordost.streetcompletegpx.quests.camping.CampType.CARAVANS_ONLY
import de.westnordost.streetcompletegpx.quests.camping.CampType.TENTS_AND_CARAVANS
import de.westnordost.streetcompletegpx.quests.camping.CampType.TENTS_ONLY

class AddCampTypeForm : AListQuestForm<CampType>() {

    override val items = listOf(
        TextItem(TENTS_AND_CARAVANS, R.string.quest_camp_type_tents_and_caravans),
        TextItem(TENTS_ONLY, R.string.quest_camp_type_tents_only),
        TextItem(CARAVANS_ONLY, R.string.quest_camp_type_caravans_only),
    )

    override val otherAnswers get() = listOfNotNull(
        AnswerItem(R.string.quest_camp_type_backcountry) { applyAnswer(BACKCOUNTRY) },
    )
}
