package de.westnordost.streetcompletegpx.quests.shop_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem

class CheckShopExistenceForm : AbstractOsmQuestForm<Unit>() {
    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { replaceShop() },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(Unit) },
    )
}
