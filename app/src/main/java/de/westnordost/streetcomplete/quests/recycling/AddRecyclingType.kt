package de.westnordost.streetcompletegpx.quests.recycling

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.recycling.RecyclingType.OVERGROUND_CONTAINER
import de.westnordost.streetcompletegpx.quests.recycling.RecyclingType.RECYCLING_CENTRE
import de.westnordost.streetcompletegpx.quests.recycling.RecyclingType.UNDERGROUND_CONTAINER

class AddRecyclingType : OsmFilterQuestType<RecyclingType>() {

    override val elementFilter = "nodes, ways with amenity = recycling and !recycling_type"
    override val changesetComment = "Specify type of recycling amenities"
    override val wikiLink = "Key:recycling_type"
    override val icon = R.drawable.ic_quest_recycling
    override val isDeleteElementEnabled = true
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_recycling_type_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes with amenity = recycling")

    override fun createForm() = AddRecyclingTypeForm()

    override fun applyAnswerTo(answer: RecyclingType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            RECYCLING_CENTRE -> {
                tags["recycling_type"] = "centre"
            }
            OVERGROUND_CONTAINER -> {
                tags["recycling_type"] = "container"
                tags["location"] = "overground"
            }
            UNDERGROUND_CONTAINER -> {
                tags["recycling_type"] = "container"
                tags["location"] = "underground"
            }
        }
    }
}
