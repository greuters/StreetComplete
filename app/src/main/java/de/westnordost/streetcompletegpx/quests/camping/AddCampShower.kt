package de.westnordost.streetcompletegpx.quests.camping

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddCampShower : OsmFilterQuestType<Boolean>() {

    /* We only resurvey shower = yes and shower = no, as it might have more detailed
     * values from other editors, and we don't want to damage them */
    override val elementFilter = """
        nodes, ways with
          tourism = camp_site and (
            !shower
            or shower older today -4 years and shower ~ yes|no
          )
    """
    override val changesetComment = "Specify whether there are showers available at camp site"
    override val wikiLink = "Key:shower"
    override val icon = R.drawable.ic_quest_shower
    override val achievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_camp_shower_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with tourism = camp_site")

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["shower"] = answer.toYesNo()
    }
}
