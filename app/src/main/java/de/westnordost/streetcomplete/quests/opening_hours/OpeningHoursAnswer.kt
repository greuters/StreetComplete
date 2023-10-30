package de.westnordost.streetcompletegpx.quests.opening_hours

import de.westnordost.streetcompletegpx.osm.opening_hours.parser.OpeningHoursRuleList

sealed interface OpeningHoursAnswer

data class RegularOpeningHours(val hours: OpeningHoursRuleList) : OpeningHoursAnswer
object AlwaysOpen : OpeningHoursAnswer
data class DescribeOpeningHours(val text: String) : OpeningHoursAnswer
object NoOpeningHoursSign : OpeningHoursAnswer
