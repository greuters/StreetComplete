package de.westnordost.streetcompletegpx.quests.diet_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.VEG
import de.westnordost.streetcompletegpx.osm.IS_SHOP_OR_DISUSED_SHOP_EXPRESSION
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

class AddVegetarian : OsmFilterQuestType<DietAvailabilityAnswer>() {

    override val elementFilter = """
        nodes, ways with
        (
          amenity ~ restaurant|cafe|fast_food|food_court and food != no
          or amenity ~ pub|nightclub|biergarten|bar and food = yes
        )
        and diet:vegan != only and (
          !diet:vegetarian
          or diet:vegetarian != only and diet:vegetarian older today -4 years
        )
    """
    override val changesetComment = "Survey whether places have vegetarian food"
    override val wikiLink = "Key:diet"
    override val icon = R.drawable.ic_quest_restaurant_vegetarian
    override val isReplaceShopEnabled = true
    override val achievements = listOf(VEG, CITIZEN)
    override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside

    override fun getTitle(tags: Map<String, String>) = R.string.quest_dietType_vegetarian_title2

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter(IS_SHOP_OR_DISUSED_SHOP_EXPRESSION)

    override fun createForm() = AddDietTypeForm.create(R.string.quest_dietType_explanation_vegetarian)

    override fun applyAnswerTo(answer: DietAvailabilityAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            is DietAvailability -> {
                tags.updateWithCheckDate("diet:vegetarian", answer.osmValue)
                if (answer.osmValue == "no") {
                    tags.remove("diet:vegan")
                }
            }
            NoFood -> tags["food"] = "no"
        }
    }
}
