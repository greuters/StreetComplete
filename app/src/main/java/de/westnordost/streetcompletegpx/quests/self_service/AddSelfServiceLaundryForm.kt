package de.westnordost.streetcompletegpx.quests.self_service

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.NO
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.ONLY
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.OPTIONAL

class AddSelfServiceLaundryForm : AbstractOsmQuestForm<SelfServiceLaundry>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_optional) { applyAnswer(OPTIONAL) },
        AnswerItem(R.string.quest_hasFeature_only) { applyAnswer(ONLY) }
    )
}
