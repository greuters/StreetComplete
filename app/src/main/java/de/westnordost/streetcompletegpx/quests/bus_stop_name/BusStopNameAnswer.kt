package de.westnordost.streetcompletegpx.quests.bus_stop_name

import de.westnordost.streetcompletegpx.osm.LocalizedName

sealed interface BusStopNameAnswer

object NoBusStopName : BusStopNameAnswer
data class BusStopName(val localizedNames: List<LocalizedName>) : BusStopNameAnswer
