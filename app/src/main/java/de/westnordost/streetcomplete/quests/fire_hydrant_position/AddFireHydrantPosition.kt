package de.westnordost.streetcompletegpx.quests.fire_hydrant_position

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.LIFESAVER
import de.westnordost.streetcompletegpx.osm.Tags

class AddFireHydrantPosition : OsmFilterQuestType<FireHydrantPosition>() {

    override val elementFilter = """
        nodes with
         emergency = fire_hydrant and
         (!fire_hydrant:position or fire_hydrant:position ~ "\?|fixme") and
         (fire_hydrant:type = pillar or fire_hydrant:type = underground)
    """
    override val changesetComment = "Specify fire hydrant positions"
    override val wikiLink = "Tag:emergency=fire_hydrant"
    override val icon = R.drawable.ic_quest_fire_hydrant_grass
    override val isDeleteElementEnabled = true
    override val achievements = listOf(LIFESAVER)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_fireHydrant_position_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes with emergency = fire_hydrant")

    override fun createForm() = AddFireHydrantPositionForm()

    override fun applyAnswerTo(answer: FireHydrantPosition, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["fire_hydrant:position"] = answer.osmValue
    }
}
