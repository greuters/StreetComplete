package de.westnordost.streetcompletegpx.quests.building_type

import de.westnordost.streetcompletegpx.quests.building_type.BuildingType.*

enum class BuildingTypeCategory(val type: BuildingType?, val subTypes: List<BuildingType>) {
    RESIDENTIAL(BuildingType.RESIDENTIAL, listOf(
        DETACHED, APARTMENTS, SEMI_DETACHED, TERRACE, HOUSE, FARM, HUT, BUNGALOW, HOUSEBOAT,
        STATIC_CARAVAN, DORMITORY
    )),
    COMMERCIAL(BuildingType.COMMERCIAL, listOf(
        OFFICE, RETAIL, KIOSK, INDUSTRIAL, WAREHOUSE, HOTEL, STORAGE_TANK, BRIDGE,
    )),
    CIVIC(BuildingType.CIVIC, listOf(
        SCHOOL, UNIVERSITY, HOSPITAL, KINDERGARTEN, SPORTS_CENTRE, TRAIN_STATION, TRANSPORTATION,
        COLLEGE, GOVERNMENT, STADIUM, FIRE_STATION, OFFICE, GRANDSTAND
    )),
    RELIGIOUS(BuildingType.RELIGIOUS, listOf(
        CHURCH, CATHEDRAL, CHAPEL, MOSQUE, TEMPLE, PAGODA, SYNAGOGUE, SHRINE
    )),
    FOR_CARS(null, listOf(
        GARAGE, GARAGES, CARPORT, PARKING
    )),
    FOR_FARMS(null, listOf(
        FARM, FARM_AUXILIARY, SILO, GREENHOUSE, STORAGE_TANK, SHED, ALLOTMENT_HOUSE
    )),
    OTHER(null, listOf(
        SHED, ROOF, GUARDHOUSE, BRIDGE, ALLOTMENT_HOUSE, SERVICE, HUT, TOILETS, HANGAR, BUNKER,
        HISTORIC, BOATHOUSE, ABANDONED, RUINS
    )),
}
