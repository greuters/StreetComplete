package de.westnordost.streetcompletegpx.quests.accepts_cards

import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryAdd
import de.westnordost.streetcompletegpx.quests.TestMapDataWithGeometry
import de.westnordost.streetcompletegpx.quests.verifyAnswer
import de.westnordost.streetcompletegpx.testutils.node
import kotlin.test.Test
import kotlin.test.assertEquals

class AddAcceptsCardsTest {
    private val questType = AddAcceptsCards()

    @Test
    fun `sets expected tags`() {
        questType.verifyAnswer(
            mapOf(),
            CardAcceptance.DEBIT_AND_CREDIT,
            StringMapEntryAdd("payment:debit_cards", "yes"),
            StringMapEntryAdd("payment:credit_cards", "yes"),
        )
    }

    @Test
    fun `applicable to greengrocer shops`() {
        val mapData = TestMapDataWithGeometry(
            listOf(
                node(1, tags = mapOf("shop" to "greengrocer", "name" to "Foobar")),
            ),
        )
        assertEquals(1, questType.getApplicableElements(mapData).toList().size)
    }
}
