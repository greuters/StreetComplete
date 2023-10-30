package de.westnordost.streetcompletegpx.data.osm.edits.create

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.MapDataWithEditsSource
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapChangesBuilder
import de.westnordost.streetcompletegpx.util.math.PositionOnWay
import de.westnordost.streetcompletegpx.util.math.PositionOnWaySegment
import de.westnordost.streetcompletegpx.util.math.VertexOfWay

fun createNodeAction(
    positionOnWay: PositionOnWay,
    mapDataWithEditsSource: MapDataWithEditsSource,
    createChanges: (StringMapChangesBuilder) -> Unit
): ElementEditAction? {
    when (positionOnWay) {
        is PositionOnWaySegment -> {
            val tagChanges = StringMapChangesBuilder(mapOf())
            createChanges(tagChanges)
            val insertIntoWayAt = InsertIntoWayAt(
                positionOnWay.wayId,
                positionOnWay.segment.first,
                positionOnWay.segment.second
            )
            return CreateNodeAction(positionOnWay.position, tagChanges, listOf(insertIntoWayAt))
        }
        is VertexOfWay -> {
            val node = mapDataWithEditsSource.getNode(positionOnWay.nodeId) ?: return null
            val tagChanges = StringMapChangesBuilder(node.tags)
            createChanges(tagChanges)
            val containingWayIds = mapDataWithEditsSource.getWaysForNode(positionOnWay.nodeId).map { it.id }
            return CreateNodeFromVertexAction(node, tagChanges.create(), containingWayIds)
        }
    }
}
