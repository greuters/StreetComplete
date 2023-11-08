package de.westnordost.streetcompletegpx.quests.police_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.quest.NoCountriesExcept
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags

class AddPoliceType : OsmFilterQuestType<PoliceType>() {

    override val elementFilter = "nodes, ways with amenity = police and !operator"
    override val changesetComment = "Specify Italian police types"
    override val wikiLink = "Tag:amenity=police"
    override val icon = R.drawable.ic_quest_police
    override val enabledInCountries = NoCountriesExcept("IT")
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_policeType_title

    override fun createForm() = AddPoliceTypeForm()

    override fun applyAnswerTo(answer: PoliceType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["operator"] = answer.operatorName
        tags["operator:wikidata"] = answer.wikidata
    }
}
