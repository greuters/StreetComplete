package de.westnordost.streetcompletegpx.quests.ferry

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CAR
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.RARE
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddFerryAccessMotorVehicle : OsmFilterQuestType<Boolean>() {

    override val elementFilter = "ways, relations with route = ferry and !motor_vehicle"
    override val changesetComment = "Specify ferry access for motor vehicles"
    override val wikiLink = "Tag:route=ferry"
    override val icon = R.drawable.ic_quest_ferry
    override val hasMarkersAtEnds = true
    override val achievements = listOf(RARE, CAR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_ferry_motor_vehicle_title

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["motor_vehicle"] = answer.toYesNo()
    }
}
