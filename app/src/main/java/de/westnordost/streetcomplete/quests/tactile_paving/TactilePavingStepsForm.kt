package de.westnordost.streetcompletegpx.quests.tactile_paving

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingStepsAnswer.BOTTOM
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingStepsAnswer.NO
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingStepsAnswer.TOP
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingStepsAnswer.YES

class TactilePavingStepsForm : AbstractOsmQuestForm<TactilePavingStepsAnswer>() {

    override val contentLayoutResId = R.layout.quest_tactile_paving

    override val otherAnswers get() = listOf(
        AnswerItem(R.string.quest_tactilePaving_steps_bottom) { applyAnswer(BOTTOM) },
        AnswerItem(R.string.quest_tactilePaving_steps_top) { applyAnswer(TOP) }
    )

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(YES) }
    )
}
