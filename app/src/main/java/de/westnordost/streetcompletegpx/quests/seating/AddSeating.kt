package de.westnordost.streetcompletegpx.quests.seating

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.IS_SHOP_OR_DISUSED_SHOP_EXPRESSION
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddSeating : OsmFilterQuestType<Seating>() {

    override val elementFilter = """
        nodes, ways with
          (
            amenity ~ restaurant|cafe|fast_food|ice_cream|food_court|pub|bar
            or shop = bakery
          )
          and takeaway != only
          and (!outdoor_seating or !indoor_seating)
    """
    override val changesetComment = "Survey whether places have seating"
    override val wikiLink = "Key:outdoor_seating"
    override val icon = R.drawable.ic_quest_seating
    override val isReplaceShopEnabled = true
    override val achievements = listOf(CITIZEN)
    override val defaultDisabledMessage = R.string.default_disabled_msg_seasonal

    override fun getTitle(tags: Map<String, String>) = R.string.quest_seating_name_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter(IS_SHOP_OR_DISUSED_SHOP_EXPRESSION)

    override fun createForm() = AddSeatingForm()

    override fun applyAnswerTo(answer: Seating, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        if (answer == Seating.NO) tags["takeaway"] = "only"
        tags["outdoor_seating"] = answer.hasOutdoorSeating.toYesNo()
        tags["indoor_seating"] = answer.hasIndoorSeating.toYesNo()
    }
}
