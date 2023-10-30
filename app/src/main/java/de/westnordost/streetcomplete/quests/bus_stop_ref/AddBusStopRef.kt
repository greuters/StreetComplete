package de.westnordost.streetcompletegpx.quests.bus_stop_ref

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.quest.NoCountriesExcept
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.osm.Tags

class AddBusStopRef : OsmFilterQuestType<BusStopRefAnswer>() {

    override val elementFilter = """
        nodes with
        (
          (public_transport = platform and ~bus|trolleybus|tram ~ yes)
          or
          (highway = bus_stop and public_transport != stop_position)
        )
        and !ref and noref != yes and ref:signed != no and !~"ref:.*"
    """
    override val enabledInCountries = NoCountriesExcept(
        "CA",
        "IE",
        "JE",
        "AU", // https://github.com/streetcomplete/StreetComplete/issues/4487
        "TR", // https://github.com/streetcomplete/StreetComplete/issues/4489
        "US",
        "IL", // https://github.com/streetcomplete/StreetComplete/issues/5119
        "CO", // https://github.com/streetcomplete/StreetComplete/issues/5124
        "NZ", // https://wiki.openstreetmap.org/w/index.php?title=Talk:StreetComplete/Quests&oldid=2599288#Quests_in_New_Zealand
    )
    override val changesetComment = "Determine bus/tram stop refs"
    override val wikiLink = "Tag:public_transport=platform"
    override val icon = R.drawable.ic_quest_bus_stop_name
    override val achievements = listOf(PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_busStopRef_title2

    override fun createForm() = AddBusStopRefForm()

    override fun applyAnswerTo(answer: BusStopRefAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            is NoVisibleBusStopRef -> tags["ref:signed"] = "no"
            is BusStopRef ->          tags["ref"] = answer.ref
        }
    }
}
