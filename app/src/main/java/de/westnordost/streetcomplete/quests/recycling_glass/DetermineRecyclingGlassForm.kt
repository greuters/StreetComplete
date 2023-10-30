package de.westnordost.streetcompletegpx.quests.recycling_glass

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.recycling_glass.RecyclingGlass.ANY
import de.westnordost.streetcompletegpx.quests.recycling_glass.RecyclingGlass.BOTTLES

class DetermineRecyclingGlassForm : AbstractOsmQuestForm<RecyclingGlass>() {
    override val contentLayoutResId = R.layout.quest_determine_recycling_glass_explanation

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_recycling_type_any_glass) { applyAnswer(ANY) },
        AnswerItem(R.string.quest_recycling_type_glass_bottles_short) { applyAnswer(BOTTLES) }
    )
}
