package de.westnordost.streetcompletegpx.quests.fire_hydrant

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.LIFESAVER
import de.westnordost.streetcompletegpx.osm.Tags

class AddFireHydrantType : OsmFilterQuestType<FireHydrantType>() {

    override val elementFilter = "nodes with emergency = fire_hydrant and !fire_hydrant:type"
    override val changesetComment = "Specify fire hydrant types"
    override val wikiLink = "Tag:emergency=fire_hydrant"
    override val icon = R.drawable.ic_quest_fire_hydrant
    override val isDeleteElementEnabled = true
    override val achievements = listOf(LIFESAVER)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_fireHydrant_type_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes with emergency = fire_hydrant")

    override fun createForm() = AddFireHydrantTypeForm()

    override fun applyAnswerTo(answer: FireHydrantType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["fire_hydrant:type"] = answer.osmValue
    }
}
