package de.westnordost.streetcompletegpx.quests.baby_changing_table

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddBabyChangingTable : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes, ways with
        (
          (
            (amenity ~ restaurant|cafe|fuel|fast_food or shop ~ mall|department_store)
            and toilets = yes
          )
          or amenity = toilets
        )
        and !diaper and !changing_table
    """
    override val changesetComment = "Survey availability of baby changing tables"
    override val wikiLink = "Key:changing_table"
    override val icon = R.drawable.ic_quest_baby
    override val isReplaceShopEnabled = true
    override val achievements = listOf(CITIZEN)
    override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside

    override fun getTitle(tags: Map<String, String>) = R.string.quest_baby_changing_table_title2

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["changing_table"] = answer.toYesNo()
    }
}
