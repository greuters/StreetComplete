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

class AddCampDrinkingWater : OsmFilterQuestType<Boolean>() {

    /* We only resurvey drinking_water = yes and drinking_water = no, as it might have more detailed
     * values from other editors, and we don't want to damage them */
    override val elementFilter = """
        nodes, ways with
          tourism = camp_site and (
            !drinking_water
            or drinking_water older today -4 years and drinking_water ~ yes|no
          )
    """
    override val changesetComment = "Specify whether there is drinking water at camp site"
    override val wikiLink = "Key:drinking_water"
    override val icon = R.drawable.ic_quest_drinking_water
    override val achievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_camp_drinking_water_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with tourism = camp_site")

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["drinking_water"] = answer.toYesNo()
    }
}
