package de.westnordost.streetcompletegpx.util.ktx

import de.westnordost.osmfeatures.GeometryType
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import de.westnordost.streetcompletegpx.data.osm.mapdata.Node
import de.westnordost.streetcompletegpx.data.osm.mapdata.Relation
import de.westnordost.streetcompletegpx.data.osm.mapdata.Way
import de.westnordost.streetcompletegpx.osm.IS_AREA_EXPRESSION

fun Element.copy(
    id: Long = this.id,
    tags: Map<String, String> = this.tags,
    version: Int = this.version,
    timestampEdited: Long = this.timestampEdited,
): Element {
    return when (this) {
        is Node -> Node(id, position, tags, version, timestampEdited)
        is Way -> Way(id, ArrayList(nodeIds), tags, version, timestampEdited)
        is Relation -> Relation(id, ArrayList(members), tags, version, timestampEdited)
    }
}

val Element.geometryType: GeometryType get() =
    when {
        type == ElementType.NODE -> GeometryType.POINT
        isArea() -> GeometryType.AREA
        type == ElementType.RELATION -> GeometryType.RELATION
        else -> GeometryType.LINE
    }

fun Element.isArea(): Boolean = when (this) {
    is Way -> isClosed && IS_AREA_EXPRESSION.matches(this)
    is Relation -> tags["type"] == "multipolygon"
    else -> false
}

fun Element.isSplittable(): Boolean = when (this) {
    is Way -> !isClosed || !IS_AREA_EXPRESSION.matches(this)
    else -> false
}

fun Element.couldBeSteps(): Boolean = when (this) {
    is Way -> !isArea() && (tags["highway"] == "footway" || tags["highway"] == "path")
    else -> false
}
