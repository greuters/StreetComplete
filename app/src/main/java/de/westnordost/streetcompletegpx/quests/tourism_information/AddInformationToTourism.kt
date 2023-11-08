package de.westnordost.streetcompletegpx.quests.tourism_information

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.RARE
import de.westnordost.streetcompletegpx.osm.Tags

class AddInformationToTourism : OsmFilterQuestType<TourismInformation>() {

    override val elementFilter = "nodes, ways with tourism = information and !information"
    override val changesetComment = "Specify type of tourist informations"
    override val wikiLink = "Tag:tourism=information"
    override val icon = R.drawable.ic_quest_information
    override val isDeleteElementEnabled = true
    override val achievements = listOf(RARE, CITIZEN, OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_tourism_information_title

    override fun createForm() = AddInformationForm()

    override fun applyAnswerTo(answer: TourismInformation, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["information"] = answer.osmValue
    }
}
