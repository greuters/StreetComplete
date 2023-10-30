package de.westnordost.streetcompletegpx.quests.religion

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.osm.Tags

class AddReligionToWaysideShrine : OsmFilterQuestType<Religion>() {

    override val elementFilter = """
        nodes, ways, relations with
          historic = wayside_shrine
          and !religion
          and access !~ private|no
    """
    override val changesetComment = "Specify religion for wayside shrines"
    override val wikiLink = "Key:religion"
    override val icon = R.drawable.ic_quest_religion
    override val achievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_religion_for_wayside_shrine_title

    override fun createForm() = AddReligionForm()

    override fun applyAnswerTo(answer: Religion, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["religion"] = answer.osmValue
    }
}
