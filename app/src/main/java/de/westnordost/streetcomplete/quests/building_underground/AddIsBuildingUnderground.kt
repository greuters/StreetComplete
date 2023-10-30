package de.westnordost.streetcompletegpx.quests.building_underground

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BUILDING
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm

class AddIsBuildingUnderground : OsmFilterQuestType<Boolean>() {

    override val elementFilter = "ways, relations with building and layer ~ -[0-9]+ and !location"
    override val changesetComment = "Determine whether buildings are fully underground"
    override val wikiLink = "Key:location"
    override val icon = R.drawable.ic_quest_building_underground
    override val achievements = listOf(BUILDING)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_building_underground_title

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["location"] = if (answer) "underground" else "surface"
    }
}
