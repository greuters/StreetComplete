package de.westnordost.streetcompletegpx.quests.max_height

import de.westnordost.streetcompletegpx.osm.Length

sealed interface MaxHeightAnswer

data class MaxHeight(val value: Length) : MaxHeightAnswer
data class NoMaxHeightSign(val isTallEnough: Boolean) : MaxHeightAnswer
