package de.westnordost.streetcompletegpx.quests.smoothness

import de.westnordost.streetcompletegpx.testutils.way
import kotlin.test.Test
import kotlin.test.assertTrue

class AddRoadSmoothnessTest {
    private val questType = AddRoadSmoothness()

    @Test fun `applicable to old way tagged with smoothness-date`() {
        assertTrue(questType.isApplicableTo(way(tags = mapOf(
            "highway" to "residential",
            "surface" to "asphalt",
            "smoothness" to "excellent",
            "smoothness:date" to "2014-10-10"
        ))))
    }
}
