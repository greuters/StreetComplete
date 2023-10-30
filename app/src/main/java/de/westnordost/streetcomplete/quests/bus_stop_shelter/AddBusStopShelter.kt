package de.westnordost.streetcompletegpx.quests.bus_stop_shelter

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.COVERED
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.NO_SHELTER
import de.westnordost.streetcompletegpx.quests.bus_stop_shelter.BusStopShelterAnswer.SHELTER

class AddBusStopShelter : OsmFilterQuestType<BusStopShelterAnswer>() {

    override val elementFilter = """
        nodes, ways, relations with
        (
          public_transport = platform
          or (highway = bus_stop and public_transport != stop_position)
        )
        and physically_present != no and naptan:BusStopType != HAR
        and !covered
        and location !~ underground|indoor
        and indoor != yes
        and tunnel != yes
        and (!level or level >= 0)
        and (!shelter or shelter older today -4 years)
    """
    /* Not asking again if it is covered because it means the stop itself is under a large
       building or roof building so this won't usually change */

    override val changesetComment = "Specify whether public transport stops have shelters"
    override val wikiLink = "Key:shelter"
    override val icon = R.drawable.ic_quest_bus_stop_shelter
    override val achievements = listOf(PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_busStopShelter_title2

    override fun createForm() = AddBusStopShelterForm()

    override fun applyAnswerTo(answer: BusStopShelterAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            SHELTER -> tags.updateWithCheckDate("shelter", "yes")
            NO_SHELTER -> tags.updateWithCheckDate("shelter", "no")
            COVERED -> {
                tags.remove("shelter")
                tags["covered"] = "yes"
            }
        }
    }
}
