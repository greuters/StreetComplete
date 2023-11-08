package de.westnordost.streetcompletegpx.quests.barrier_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BICYCLIST
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BLIND
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CAR
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.Tags

class AddBarrierType : OsmFilterQuestType<BarrierType>() {

    override val elementFilter = """
        nodes with barrier = yes
         and !man_made
         and !historic
         and !military
         and !power
         and !tourism
         and !attraction
         and !amenity
         and !leisure
         and !aeroway
         and !railway
         and !craft
         and !healthcare
         and !office
         and !shop
    """
    override val changesetComment = "Specify type of barriers"
    override val wikiLink = "Key:barrier"
    override val icon = R.drawable.ic_quest_barrier
    override val isDeleteElementEnabled = true
    override val achievements = listOf(CAR, PEDESTRIAN, BLIND, WHEELCHAIR, BICYCLIST, OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_barrier_type_title

    override fun createForm() = AddBarrierTypeForm()

    override fun applyAnswerTo(answer: BarrierType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) =
        answer.applyTo(tags)
}
