package de.westnordost.streetcompletegpx.quests.wheelchair_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.LIMITED
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.NO
import de.westnordost.streetcompletegpx.quests.wheelchair_access.WheelchairAccess.YES

class AddWheelchairAccessToiletsPartForm : AbstractOsmQuestForm<WheelchairAccessToiletsPartAnswer>() {
    override val contentLayoutResId = R.layout.quest_wheelchair_toilets_explanation

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(WheelchairAccessToiletsPart(NO)) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(WheelchairAccessToiletsPart(YES)) },
        AnswerItem(R.string.quest_wheelchairAccess_limited) { applyAnswer(WheelchairAccessToiletsPart(LIMITED)) },
    )

    override val otherAnswers get() = listOf(
        AnswerItem(R.string.quest_wheelchairAccessPat_noToilet) { applyAnswer(NoToilet) }
    )
}
