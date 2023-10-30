package de.westnordost.streetcompletegpx.data.osm.mapdata

import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementPointGeometry

interface MapDataWithGeometry : MapData {
    fun getNodeGeometry(id: Long): ElementPointGeometry?
    fun getWayGeometry(id: Long): ElementGeometry?
    fun getRelationGeometry(id: Long): ElementGeometry?

    fun getGeometry(elementType: ElementType, id: Long): ElementGeometry? = when (elementType) {
        ElementType.NODE -> getNodeGeometry(id)
        ElementType.WAY -> getWayGeometry(id)
        ElementType.RELATION -> getRelationGeometry(id)
    }
}
