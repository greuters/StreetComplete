package de.westnordost.streetcompletegpx.data.elementfilter.filters

import de.westnordost.streetcompletegpx.data.elementfilter.matches
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HasTagTest {
    val c = HasTag("highway", "residential")

    @Test fun matches() {
        assertTrue(c.matches(mapOf("highway" to "residential")))
        assertFalse(c.matches(mapOf("highway" to "residental")))
        assertFalse(c.matches(mapOf("hipway" to "residential")))
        assertFalse(c.matches(mapOf()))
    }

    @Test fun toStringMethod() {
        assertEquals("highway = residential", c.toString())
    }
}
