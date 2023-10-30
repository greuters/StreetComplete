package de.westnordost.streetcompletegpx.quests.place_name

import de.westnordost.streetcompletegpx.osm.LocalizedName

sealed interface PlaceNameAnswer

data class PlaceName(val localizedNames: List<LocalizedName>) : PlaceNameAnswer
object NoPlaceNameSign : PlaceNameAnswer
