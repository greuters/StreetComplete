package de.westnordost.streetcompletegpx.data.osm.osmquests

import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter

/** Quest type where each quest refers to one OSM element where the element selection is based on
 *  a simple [element filter expression][de.westnordost.streetcompletegpx.data.elementfilter.ElementFilterExpression].
 */
abstract class OsmFilterQuestType<T> : OsmElementQuestType<T> {

    val filter by lazy { elementFilter.toElementFilterExpression() }

    protected abstract val elementFilter: String

    override fun getApplicableElements(mapData: MapDataWithGeometry): Iterable<Element> =
        mapData.filter(elementFilter).asIterable()

    override fun isApplicableTo(element: Element) = filter.matches(element)
}
