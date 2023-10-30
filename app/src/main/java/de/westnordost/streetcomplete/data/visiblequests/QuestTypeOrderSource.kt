package de.westnordost.streetcompletegpx.data.visiblequests

import de.westnordost.streetcompletegpx.data.quest.QuestType

interface QuestTypeOrderSource {

    /** interface to be notified of changes in quest type order */
    interface Listener {
        /** Called when a new order item was added */
        fun onQuestTypeOrderAdded(item: QuestType, toAfter: QuestType)
        /** Called when the orders changed */
        fun onQuestTypeOrdersChanged()
    }

    /** sort given quest types by user's custom order, if any */
    fun sort(questTypes: MutableList<QuestType>, presetId: Long? = null)

    fun getOrders(presetId: Long? = null): List<Pair<QuestType, QuestType>>

    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)
}
