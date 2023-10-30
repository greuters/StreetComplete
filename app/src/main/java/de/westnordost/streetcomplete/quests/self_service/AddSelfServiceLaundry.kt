package de.westnordost.streetcompletegpx.quests.self_service

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.NO
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.ONLY
import de.westnordost.streetcompletegpx.quests.self_service.SelfServiceLaundry.OPTIONAL

class AddSelfServiceLaundry : OsmFilterQuestType<SelfServiceLaundry>() {

    override val elementFilter = "nodes, ways with shop = laundry and !self_service"
    override val changesetComment = "Survey whether laundries provide self-service"
    override val wikiLink = "Tag:shop=laundry"
    override val icon = R.drawable.ic_quest_laundry
    override val isReplaceShopEnabled = true
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_laundrySelfService_title2

    override fun createForm() = AddSelfServiceLaundryForm()

    override fun applyAnswerTo(answer: SelfServiceLaundry, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            NO -> {
                tags["self_service"] = "no"
                tags["laundry_service"] = "yes"
            }
            OPTIONAL -> {
                tags["self_service"] = "yes"
                tags["laundry_service"] = "yes"
            }
            ONLY -> {
                tags["self_service"] = "yes"
                tags["laundry_service"] = "no"
            }
        }
    }
}
