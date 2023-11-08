package de.westnordost.streetcompletegpx.quests.surface

import de.westnordost.streetcompletegpx.osm.surface.SurfaceAndNote

sealed interface SurfaceOrIsStepsAnswer
object IsActuallyStepsAnswer : SurfaceOrIsStepsAnswer
object IsIndoorsAnswer : SurfaceOrIsStepsAnswer
data class SurfaceAnswer(val value: SurfaceAndNote) : SurfaceOrIsStepsAnswer
