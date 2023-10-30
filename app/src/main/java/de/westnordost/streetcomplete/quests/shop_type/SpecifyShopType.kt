package de.westnordost.streetcompletegpx.quests.shop_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.IS_SHOP_OR_DISUSED_SHOP_EXPRESSION
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.removeCheckDates

class SpecifyShopType : OsmFilterQuestType<ShopTypeAnswer>() {

    override val elementFilter = """
        nodes, ways with (
         shop = yes
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
        )
    """
    override val changesetComment = "Survey shop types"
    override val wikiLink = "Key:shop"
    override val icon = R.drawable.ic_quest_shop
    override val isReplaceShopEnabled = true
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_shop_type_title2

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter(IS_SHOP_OR_DISUSED_SHOP_EXPRESSION)

    override fun createForm() = ShopTypeForm()

    override fun applyAnswerTo(answer: ShopTypeAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.removeCheckDates()
        when (answer) {
            is IsShopVacant -> {
                tags.remove("shop")
                tags["disused:shop"] = "yes"
            }
            is ShopType -> {
                tags.remove("disused:shop")
                if (!answer.tags.containsKey("shop")) {
                    tags.remove("shop")
                }
                for ((key, value) in answer.tags) {
                    tags[key] = value
                }
            }
        }
    }
}
