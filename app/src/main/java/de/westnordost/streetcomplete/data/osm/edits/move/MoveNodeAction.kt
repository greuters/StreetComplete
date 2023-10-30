package de.westnordost.streetcompletegpx.data.osm.edits.move

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.ElementIdProvider
import de.westnordost.streetcompletegpx.data.osm.edits.IsActionRevertable
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.isGeometrySubstantiallyDifferent
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataChanges
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataRepository
import de.westnordost.streetcompletegpx.data.osm.mapdata.Node
import de.westnordost.streetcompletegpx.data.osm.mapdata.key
import de.westnordost.streetcompletegpx.data.upload.ConflictException
import de.westnordost.streetcompletegpx.util.ktx.nowAsEpochMilliseconds
import kotlinx.serialization.Serializable

/** Action that moves a node. */
@Serializable
data class MoveNodeAction(
    val originalNode: Node,
    val position: LatLon
) : ElementEditAction, IsActionRevertable {

    override val elementKeys get() = listOf(originalNode.key)

    override fun idsUpdatesApplied(updatedIds: Map<ElementKey, Long>) = copy(
        originalNode = originalNode.copy(id = updatedIds[originalNode.key] ?: originalNode.id)
    )

    override fun createUpdates(
        mapDataRepository: MapDataRepository,
        idProvider: ElementIdProvider
    ): MapDataChanges {
        val currentNode = mapDataRepository.getNode(originalNode.id)
            ?: throw ConflictException("Element deleted")
        val node = currentNode as? Node ?: throw ConflictException("Element deleted")

        if (isGeometrySubstantiallyDifferent(originalNode, currentNode)) {
            throw ConflictException("Element geometry changed substantially")
        }

        return MapDataChanges(modifications = listOf(node.copy(
            position = position,
            timestampEdited = nowAsEpochMilliseconds()
        )))
    }

    override fun createReverted(idProvider: ElementIdProvider) =
        RevertMoveNodeAction(originalNode)
}
