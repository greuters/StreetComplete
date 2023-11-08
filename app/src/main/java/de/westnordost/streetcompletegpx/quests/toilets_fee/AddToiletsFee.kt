package de.westnordost.streetcompletegpx.quests.toilets_fee

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.quest.AllCountriesExcept
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddToiletsFee : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways with
          amenity = toilets
          and access !~ private|customers
          and !fee
          and (!seasonal or seasonal = no)
    """
    override val changesetComment = "Specify toilet fees"
    override val wikiLink = "Key:fee"
    override val icon = R.drawable.ic_quest_toilet_fee
    override val enabledInCountries = AllCountriesExcept("US", "CA")
    override val isDeleteElementEnabled = true
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_toiletsFee_title

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["fee"] = answer.toYesNo()
    }
}
