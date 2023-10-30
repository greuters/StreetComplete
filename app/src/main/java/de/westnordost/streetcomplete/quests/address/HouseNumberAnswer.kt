package de.westnordost.streetcompletegpx.quests.address

import de.westnordost.streetcompletegpx.osm.address.AddressNumber

sealed interface HouseNumberAnswer

data class AddressNumberOrName(val number: AddressNumber?, val name: String?) : HouseNumberAnswer
object WrongBuildingType : HouseNumberAnswer
