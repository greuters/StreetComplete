package de.westnordost.streetcompletegpx.quests.playground_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.playground_access.PlaygroundAccess.CUSTOMERS
import de.westnordost.streetcompletegpx.quests.playground_access.PlaygroundAccess.PRIVATE
import de.westnordost.streetcompletegpx.quests.playground_access.PlaygroundAccess.YES

class AddPlaygroundAccessForm : AListQuestForm<PlaygroundAccess>() {

    override val items = listOf(
        TextItem(YES, R.string.quest_access_yes),
        TextItem(CUSTOMERS, R.string.quest_access_customers),
        TextItem(PRIVATE, R.string.quest_access_private),
    )
}
