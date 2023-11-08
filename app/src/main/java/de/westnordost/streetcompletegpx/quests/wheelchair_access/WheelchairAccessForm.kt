package de.westnordost.streetcompletegpx.quests.wheelchair_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.LIMITED
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.NO
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.YES

open class WheelchairAccessForm : AbstractOsmQuestForm<WheelchairAccess>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(YES) },
        AnswerItem(R.string.quest_wheelchairAccess_limited) { applyAnswer(LIMITED) },
    )
}
