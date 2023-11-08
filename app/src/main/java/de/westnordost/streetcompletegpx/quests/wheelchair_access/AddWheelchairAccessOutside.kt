package de.westnordost.streetcompletegpx.quests.wheelchair_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.RARE
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

class AddWheelchairAccessOutside : OsmFilterQuestType<WheelchairAccess>() {

    override val elementFilter = """
        nodes, ways, relations with
         leisure = dog_park
         and access !~ no|private
         and (!wheelchair or wheelchair older today -8 years)
    """
    override val changesetComment = "Survey wheelchair accessibility of outside places"
    override val wikiLink = "Key:wheelchair"
    override val icon = R.drawable.ic_quest_toilets_wheelchair
    override val achievements = listOf(RARE, WHEELCHAIR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_wheelchairAccess_outside_title

    override fun createForm() = AddWheelchairAccessOutsideForm()

    override fun applyAnswerTo(answer: WheelchairAccess, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("wheelchair", answer.osmValue)
    }
}
