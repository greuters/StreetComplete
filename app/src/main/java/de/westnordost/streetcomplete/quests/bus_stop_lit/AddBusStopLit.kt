package de.westnordost.streetcompletegpx.quests.bus_stop_lit

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddBusStopLit : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways, relations with
        (
          public_transport = platform
          or (highway = bus_stop and public_transport != stop_position)
        )
        and physically_present != no and naptan:BusStopType != HAR
        and location !~ underground|indoor
        and indoor != yes
        and (!level or level >= 0)
        and (
          !lit
          or lit = no and lit older today -8 years
          or lit older today -16 years
        )
    """
    override val changesetComment = "Add whether public transport stops are lit"
    override val wikiLink = "Key:lit"
    override val icon = R.drawable.ic_quest_bus_stop_lit
    override val achievements = listOf(PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_busStopLit_title2

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("lit", answer.toYesNo())
    }
}
