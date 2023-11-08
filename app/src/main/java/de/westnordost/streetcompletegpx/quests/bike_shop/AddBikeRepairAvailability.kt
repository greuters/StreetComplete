package de.westnordost.streetcompletegpx.quests.bike_shop

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BICYCLIST
import de.westnordost.streetcompletegpx.osm.IS_SHOP_OR_DISUSED_SHOP_EXPRESSION
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddBikeRepairAvailability : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways with shop = bicycle
        and (
            !service:bicycle:repair
            or service:bicycle:repair older today -6 years
        )
        and access !~ private|no
    """

    override val changesetComment = "Specify whether bicycle shops offer repairs"
    override val wikiLink = "Key:service:bicycle:repair"
    override val icon = R.drawable.ic_quest_bicycle_repair
    override val achievements = listOf(BICYCLIST)
    override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside

    override fun getTitle(tags: Map<String, String>) = R.string.quest_bicycle_shop_repair_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter(IS_SHOP_OR_DISUSED_SHOP_EXPRESSION)

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("service:bicycle:repair", answer.toYesNo())
    }
}
