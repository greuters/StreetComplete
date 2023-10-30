package de.westnordost.streetcompletegpx.quests.parking_fee

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CAR
import de.westnordost.streetcompletegpx.osm.Tags

class AddParkingFee : OsmFilterQuestType<FeeAndMaxStay>() {

    override val elementFilter = """
        nodes, ways, relations with amenity = parking
        and access ~ yes|customers|public
        and (
            !fee and !fee:conditional
            or fee older today -8 years
        )
    """
    override val changesetComment = "Specify whether parking requires a fee"
    override val wikiLink = "Tag:amenity=parking"
    override val icon = R.drawable.ic_quest_parking_fee
    override val achievements = listOf(CAR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_parking_fee_title

    override fun createForm() = AddParkingFeeForm()

    override fun applyAnswerTo(answer: FeeAndMaxStay, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) =
        answer.applyTo(tags)
}
