package de.westnordost.streetcompletegpx.quests.general_fee

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddGeneralFee : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways with
         (tourism = museum or leisure = beach_resort or tourism = gallery or tourism = caravan_site)
         and access !~ private|no
         and !fee
    """
    override val changesetComment = "Specify whether places take fees to visit"
    override val wikiLink = "Key:fee"
    override val icon = R.drawable.ic_quest_fee
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_generalFee_title2

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["fee"] = answer.toYesNo()
    }
}
