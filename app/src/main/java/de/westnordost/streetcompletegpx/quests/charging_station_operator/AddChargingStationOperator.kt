package de.westnordost.streetcompletegpx.quests.charging_station_operator

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CAR
import de.westnordost.streetcompletegpx.osm.Tags

class AddChargingStationOperator : OsmFilterQuestType<String>() {

    override val elementFilter = """
        nodes, ways with
          amenity = charging_station
          and !operator and !name and !brand
    """
    override val changesetComment = "Specify charging station operators"
    override val wikiLink = "Tag:amenity=charging_station"
    override val icon = R.drawable.ic_quest_car_charger
    override val isDeleteElementEnabled = true
    override val achievements = listOf(CAR)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_charging_station_operator_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with amenity = charging_station")

    override fun createForm() = AddChargingStationOperatorForm()

    override fun applyAnswerTo(answer: String, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["operator"] = answer
    }
}
