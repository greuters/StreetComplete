package de.westnordost.streetcompletegpx.quests.traffic_signals_vibrate

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem

class AddTrafficSignalsVibrationForm : AbstractOsmQuestForm<Boolean>() {

    override val contentLayoutResId = R.layout.quest_traffic_lights_vibration

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(false) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(true) }
    )
}
