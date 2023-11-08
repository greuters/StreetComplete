package de.westnordost.streetcompletegpx.quests.way_lit

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.osm.lit.LitStatus.AUTOMATIC
import de.westnordost.streetcompletegpx.osm.lit.LitStatus.NIGHT_AND_DAY
import de.westnordost.streetcompletegpx.osm.lit.LitStatus.NO
import de.westnordost.streetcompletegpx.osm.lit.LitStatus.YES
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.util.ktx.couldBeSteps

class WayLitForm : AbstractOsmQuestForm<WayLitOrIsStepsAnswer>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(WayLit(NO)) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(WayLit(YES)) }
    )

    override val otherAnswers get() = listOfNotNull(
        AnswerItem(R.string.lit_value_24_7) { applyAnswer(WayLit(NIGHT_AND_DAY)) },
        AnswerItem(R.string.lit_value_automatic) { applyAnswer(WayLit(AUTOMATIC)) },
        createConvertToStepsAnswer(),
    )

    private fun createConvertToStepsAnswer(): AnswerItem? {
        return if (element.couldBeSteps()) {
            AnswerItem(R.string.quest_generic_answer_is_actually_steps) {
                applyAnswer(IsActuallyStepsAnswer)
            }
        } else null
    }
}
