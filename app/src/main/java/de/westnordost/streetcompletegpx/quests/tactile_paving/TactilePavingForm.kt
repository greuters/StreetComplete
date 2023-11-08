package de.westnordost.streetcompletegpx.quests.tactile_paving

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem

class TactilePavingForm : AbstractOsmQuestForm<Boolean>() {

    override val contentLayoutResId = R.layout.quest_tactile_paving

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(false) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(true) }
    )
}
