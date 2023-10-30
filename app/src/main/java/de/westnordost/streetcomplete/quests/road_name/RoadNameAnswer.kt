package de.westnordost.streetcompletegpx.quests.road_name

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.osm.LocalizedName

sealed interface RoadNameAnswer

data class RoadName(
    val localizedNames: List<LocalizedName>,
    val wayId: Long,
    val wayGeometry: List<LatLon>
) : RoadNameAnswer

object NoRoadName : RoadNameAnswer
object RoadIsServiceRoad : RoadNameAnswer
object RoadIsTrack : RoadNameAnswer
object RoadIsLinkRoad : RoadNameAnswer
