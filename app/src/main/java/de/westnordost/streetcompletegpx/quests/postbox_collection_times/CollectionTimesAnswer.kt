package de.westnordost.streetcompletegpx.quests.postbox_collection_times

import de.westnordost.streetcompletegpx.osm.opening_hours.parser.OpeningHoursRuleList

sealed interface CollectionTimesAnswer

data class CollectionTimes(val times: OpeningHoursRuleList) : CollectionTimesAnswer
object NoCollectionTimesSign : CollectionTimesAnswer
