package de.westnordost.streetcompletegpx.quests.tactile_paving

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BLIND
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddTactilePavingBusStop : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways, relations with
        (
          public_transport = platform
          or (highway = bus_stop and public_transport != stop_position)
        )
        and physically_present != no and naptan:BusStopType != HAR
        and (
          !tactile_paving
          or tactile_paving = unknown
          or tactile_paving = no and tactile_paving older today -4 years
          or tactile_paving = yes and tactile_paving older today -8 years
        )
    """
    override val changesetComment = "Specify whether public transport stops have tactile paving"
    override val wikiLink = "Key:tactile_paving"
    override val icon = R.drawable.ic_quest_blind_bus
    override val enabledInCountries = COUNTRIES_WHERE_TACTILE_PAVING_IS_COMMON
    override val achievements = listOf(BLIND)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_busStopTactilePaving_title

    override fun createForm() = TactilePavingForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("tactile_paving", answer.toYesNo())
    }
}
