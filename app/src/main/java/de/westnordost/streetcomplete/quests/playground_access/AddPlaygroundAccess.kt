package de.westnordost.streetcompletegpx.quests.playground_access

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags

class AddPlaygroundAccess : OsmFilterQuestType<PlaygroundAccess>() {

    override val elementFilter = "nodes, ways, relations with leisure = playground and (!access or access = unknown)"
    override val changesetComment = "Specify access to playgrounds"
    override val wikiLink = "Tag:leisure=playground"
    override val icon = R.drawable.ic_quest_playground
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_playground_access_title2

    override fun createForm() = AddPlaygroundAccessForm()

    override fun applyAnswerTo(answer: PlaygroundAccess, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["access"] = answer.osmValue
    }
}
