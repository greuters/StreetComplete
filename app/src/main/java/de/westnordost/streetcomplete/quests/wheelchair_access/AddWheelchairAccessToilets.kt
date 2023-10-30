package de.westnordost.streetcompletegpx.quests.wheelchair_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

class AddWheelchairAccessToilets : OsmFilterQuestType<WheelchairAccess>() {

    override val elementFilter = """
        nodes, ways with amenity = toilets
         and access !~ no|private
         and (
           !wheelchair
           or wheelchair != yes and wheelchair older today -4 years
           or wheelchair older today -8 years
         )
    """
    override val changesetComment = "Specify wheelchair accessibility of toilets"
    override val wikiLink = "Key:wheelchair"
    override val icon = R.drawable.ic_quest_toilets_wheelchair
    override val isDeleteElementEnabled = true
    override val achievements = listOf(WHEELCHAIR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_wheelchairAccess_outside_title

    override fun createForm() = AddWheelchairAccessToiletsForm()

    override fun applyAnswerTo(answer: WheelchairAccess, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("wheelchair", answer.osmValue)
    }
}
