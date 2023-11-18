package de.westnordost.streetcompletegpx.quests.camping

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.camping.CampType.BACKCOUNTRY
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddCampType : OsmFilterQuestType<CampType>() {

    override val elementFilter = """
        nodes, ways with
          tourism = camp_site
          and (!caravans or !tents)
          and !backcountry
    """
    override val changesetComment = "Survey who may camp here"
    override val wikiLink = "Key:caravans"
    override val icon = R.drawable.ic_quest_tent
    // you can often see caravans and/or tents from the outside, so usually there is no need for this quest to be disabled by default
    // override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside
    override val achievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_camp_type_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with tourism = camp_site")

    override fun createForm() = AddCampTypeForm()

    override fun applyAnswerTo(answer: CampType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            BACKCOUNTRY -> tags["backcountry"] = "yes"
            else -> {
                tags["tents"] = answer.tents.toYesNo()
                tags["caravans"] = answer.caravans.toYesNo()
            }
        }
    }
}
