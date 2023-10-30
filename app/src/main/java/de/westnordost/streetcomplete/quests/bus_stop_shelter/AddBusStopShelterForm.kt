package de.westnordost.streetcompletegpx.quests.bus_stop_shelter

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.COVERED
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.NO_SHELTER
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.SHELTER

class AddBusStopShelterForm : AbstractOsmQuestForm<BusStopShelterAnswer>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO_SHELTER) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(SHELTER) }
    )

    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_busStopShelter_covered) { applyAnswer(COVERED) }
    )
}
