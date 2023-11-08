package de.westnordost.streetcompletegpx.quests.drinking_water

import de.westnordost.streetcompletegpx.quests.TestMapDataWithGeometry
import de.westnordost.streetcompletegpx.testutils.node
import kotlin.test.Test
import kotlin.test.assertEquals

class AddDrinkingWaterTest {
    private val questType = AddDrinkingWater()

    @Test
    fun `applicable to springs`() {
        val mapData = TestMapDataWithGeometry(
            listOf(
                node(1, tags = mapOf("natural" to "spring")),
            ),
        )
        assertEquals(1, questType.getApplicableElements(mapData).toList().size)
    }

    @Test
    fun `not applicable to inaccessible springs`() {
        val mapData = TestMapDataWithGeometry(
            listOf(
                node(1, tags = mapOf("natural" to "spring", "access" to "no")),
            ),
        )
        assertEquals(0, questType.getApplicableElements(mapData).toList().size)
    }
}
