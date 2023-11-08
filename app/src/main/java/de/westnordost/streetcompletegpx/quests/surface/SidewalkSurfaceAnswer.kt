package de.westnordost.streetcompletegpx.quests.surface

import de.westnordost.streetcompletegpx.osm.sidewalk_surface.LeftAndRightSidewalkSurface

sealed interface SidewalkSurfaceAnswer

data class SidewalkSurface(val value: LeftAndRightSidewalkSurface) : SidewalkSurfaceAnswer
object SidewalkIsDifferent : SidewalkSurfaceAnswer
