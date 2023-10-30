package de.westnordost.streetcompletegpx.quests.way_lit

import de.westnordost.streetcompletegpx.osm.lit.LitStatus

sealed interface WayLitOrIsStepsAnswer
object IsActuallyStepsAnswer : WayLitOrIsStepsAnswer
data class WayLit(val litStatus: LitStatus) : WayLitOrIsStepsAnswer
