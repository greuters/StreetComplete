package de.westnordost.streetcompletegpx.quests.grit_bin_seasonal

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm

class AddGritBinSeasonal : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes with
          amenity = grit_bin
          and !seasonal
    """
    override val changesetComment = "Specify whether grit bins are seasonal"
    override val wikiLink = "Key:seasonal"
    override val icon = R.drawable.ic_quest_calendar
    override val achievements = listOf(CITIZEN)
    override val defaultDisabledMessage = R.string.default_disabled_msg_seasonal

    override fun getTitle(tags: Map<String, String>) = R.string.quest_gritBinSeasonal_title

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("seasonal", if (answer) "no" else "winter")
    }
}
