package de.westnordost.streetcompletegpx.quests.tactile_paving

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingCrosswalkAnswer.INCORRECT
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingCrosswalkAnswer.NO
import de.westnordost.streetcompletegpx.quests.tactile_paving.TactilePavingCrosswalkAnswer.YES

class TactilePavingCrosswalkForm : AbstractOsmQuestForm<TactilePavingCrosswalkAnswer>() {

    override val contentLayoutResId = R.layout.quest_tactile_paving

    override val otherAnswers get() = listOf(
        AnswerItem(R.string.quest_tactilePaving_incorrect) { applyAnswer(INCORRECT) }
    )

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(YES) }
    )
}
