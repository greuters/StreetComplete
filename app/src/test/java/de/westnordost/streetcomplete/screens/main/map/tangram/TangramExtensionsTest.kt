package de.westnordost.streetcompletegpx.screens.main.map.tangram

import de.westnordost.streetcompletegpx.testutils.p
import kotlin.test.Test
import kotlin.test.assertEquals

class TangramExtensionsTest {

    @Test fun `convert single`() {
        val pos = p(5.0, 10.0)
        val pos2 = pos.toLngLat().toLatLon()

        assertEquals(pos, pos2)
    }
}
