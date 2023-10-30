package de.westnordost.streetcompletegpx.quests.crossing_type

enum class CrossingType(val osmValue: String) {
    TRAFFIC_SIGNALS("traffic_signals"),
    MARKED("marked"),
    UNMARKED("unmarked"),
}
