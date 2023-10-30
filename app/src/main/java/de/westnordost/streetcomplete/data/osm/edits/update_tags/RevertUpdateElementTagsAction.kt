package de.westnordost.streetcompletegpx.data.osm.edits.update_tags

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.ElementIdProvider
import de.westnordost.streetcompletegpx.data.osm.edits.IsRevertAction
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataChanges
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataRepository
import de.westnordost.streetcompletegpx.data.osm.mapdata.key
import de.westnordost.streetcompletegpx.data.upload.ConflictException
import de.westnordost.streetcompletegpx.util.ktx.copy
import kotlinx.serialization.Serializable

/** Contains the information necessary to apply a revert of tag changes made on an element */
@Serializable
data class RevertUpdateElementTagsAction(
    val originalElement: Element,
    val changes: StringMapChanges
) : ElementEditAction, IsRevertAction {

    override val elementKeys get() = listOf(originalElement.key)

    override fun idsUpdatesApplied(updatedIds: Map<ElementKey, Long>) = copy(
        originalElement = originalElement.copy(id = updatedIds[originalElement.key] ?: originalElement.id)
    )

    override fun createUpdates(
        mapDataRepository: MapDataRepository,
        idProvider: ElementIdProvider
    ): MapDataChanges {
        val currentElement = mapDataRepository.get(originalElement.type, originalElement.id)
            ?: throw ConflictException("Element deleted")

        if (isGeometrySubstantiallyDifferent(originalElement, currentElement)) {
            throw ConflictException("Element geometry changed substantially")
        }

        return MapDataChanges(modifications = listOf(currentElement.changesApplied(changes)))
    }
}
