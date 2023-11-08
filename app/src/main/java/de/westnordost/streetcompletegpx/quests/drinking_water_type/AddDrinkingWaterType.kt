package de.westnordost.streetcompletegpx.quests.drinking_water_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.osm.Tags

class AddDrinkingWaterType : OsmFilterQuestType<DrinkingWaterType>() {

    override val elementFilter = """
        nodes with
        (
            (amenity = drinking_water and !disused:amenity)
            or
            (disused:amenity = drinking_water and !amenity and older today -1 years)
        )
        and (!seasonal or seasonal = no)
        and !man_made and !natural and !fountain and !pump
    """

    override val changesetComment = "Specify drinking water types"
    override val wikiLink = "Tag:amenity=drinking_water"
    override val icon = R.drawable.ic_quest_drinking_water // another icon?
    override val isDeleteElementEnabled = true
    override val achievements = listOf(CITIZEN, OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_drinking_water_type_title2

    override fun createForm() = AddDrinkingWaterTypeForm()

    override fun applyAnswerTo(answer: DrinkingWaterType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        answer.applyTo(tags)
    }
}
