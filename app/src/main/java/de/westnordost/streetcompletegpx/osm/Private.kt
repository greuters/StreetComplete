package de.westnordost.streetcompletegpx.osm

import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element

private val isPrivateOnFootFilter by lazy { """
    nodes, ways, relations with
      access ~ private|no
      and (!foot or foot ~ private|no)
""".toElementFilterExpression() }

fun isPrivateOnFoot(element: Element): Boolean = isPrivateOnFootFilter.matches(element)
