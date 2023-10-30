package de.westnordost.streetcompletegpx.quests.internet_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AListQuestForm
import de.westnordost.streetcompletegpx.quests.TextItem
import de.westnordost.streetcompletegpx.quests.internet_access.InternetAccess.NO
import de.westnordost.streetcompletegpx.quests.internet_access.InternetAccess.TERMINAL
import de.westnordost.streetcompletegpx.quests.internet_access.InternetAccess.WIFI
import de.westnordost.streetcompletegpx.quests.internet_access.InternetAccess.WIRED

class AddInternetAccessForm : AListQuestForm<InternetAccess>() {

    override val items = listOf(
        TextItem(WIFI, R.string.quest_internet_access_wlan),
        TextItem(NO, R.string.quest_internet_access_no),
        TextItem(TERMINAL, R.string.quest_internet_access_terminal),
        TextItem(WIRED, R.string.quest_internet_access_wired),
    )
}
