package de.westnordost.streetcompletegpx.quests.incline_direction

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.osm.Tags

class AddStepsIncline : OsmFilterQuestType<Incline>() {

    override val elementFilter = """
        ways with highway = steps
         and (!indoor or indoor = no)
         and area != yes
         and access !~ private|no
         and !incline
    """
    override val changesetComment = "Specify which way leads up for steps"
    override val wikiLink = "Key:incline"
    override val icon = R.drawable.ic_quest_steps
    override val achievements = listOf(PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_steps_incline_title

    override fun createForm() = AddInclineForm()

    override fun applyAnswerTo(answer: Incline, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) =
        answer.applyTo(tags)
}
