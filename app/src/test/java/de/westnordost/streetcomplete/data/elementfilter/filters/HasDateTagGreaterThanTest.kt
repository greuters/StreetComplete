package de.westnordost.streetcompletegpx.data.elementfilter.filters

import de.westnordost.streetcompletegpx.data.elementfilter.matches
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HasDateTagGreaterThanTest {
    private val date = LocalDate(2000, 11, 11)
    private val c = HasDateTagGreaterThan("check_date", FixedDate(date))

    @Test fun matches() {
        assertFalse(c.matches(mapOf()))
        assertFalse(c.matches(mapOf("check_date" to "bla")))
        assertTrue(c.matches(mapOf("check_date" to "2000-11-12")))
        assertFalse(c.matches(mapOf("check_date" to "2000-11-11")))
        assertFalse(c.matches(mapOf("check_date" to "2000-11-10")))
    }

    @Test fun toStringMethod() {
        assertEquals("check_date > $date", c.toString())
    }
}
