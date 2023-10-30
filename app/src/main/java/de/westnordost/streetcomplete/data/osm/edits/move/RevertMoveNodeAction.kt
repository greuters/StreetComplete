package de.westnordost.streetcompletegpx.data.osm.edits.move

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.ElementIdProvider
import de.westnordost.streetcompletegpx.data.osm.edits.IsRevertAction
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataChanges
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataRepository
import de.westnordost.streetcompletegpx.data.osm.mapdata.Node
import de.westnordost.streetcompletegpx.data.osm.mapdata.key
import de.westnordost.streetcompletegpx.data.upload.ConflictException
import de.westnordost.streetcompletegpx.util.ktx.nowAsEpochMilliseconds
import kotlinx.serialization.Serializable

/** Action reverts moving a node. */
@Serializable
data class RevertMoveNodeAction(
    val originalNode: Node,
) : ElementEditAction, IsRevertAction {

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

        return MapDataChanges(modifications = listOf(currentNode.copy(
            position = originalNode.position,
            timestampEdited = nowAsEpochMilliseconds()
        )))
    }
}
