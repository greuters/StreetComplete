package de.westnordost.streetcompletegpx.quests.wheelchair_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

class AddWheelchairAccessPublicTransport : OsmFilterQuestType<WheelchairAccess>() {

    override val elementFilter = """
        nodes, ways, relations with
         (amenity = bus_station or railway ~ station|subway_entrance)
         and access !~ no|private
         and (
          !wheelchair
          or wheelchair != yes and wheelchair older today -4 years
          or wheelchair older today -8 years
         )
    """
    override val changesetComment = "Survey wheelchair accessibility of public transport platforms"
    override val wikiLink = "Key:wheelchair"
    override val icon = R.drawable.ic_quest_wheelchair
    override val achievements = listOf(WHEELCHAIR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_wheelchairAccess_outside_title

    override fun createForm() = AddWheelchairAccessPublicTransportForm()

    override fun applyAnswerTo(answer: WheelchairAccess, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("wheelchair", answer.osmValue)
    }
}
