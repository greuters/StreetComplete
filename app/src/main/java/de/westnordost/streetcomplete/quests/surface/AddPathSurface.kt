package de.westnordost.streetcompletegpx.quests.surface

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BICYCLIST
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.changeToSteps
import de.westnordost.streetcompletegpx.osm.surface.ANYTHING_UNPAVED
import de.westnordost.streetcompletegpx.osm.surface.INVALID_SURFACES
import de.westnordost.streetcompletegpx.osm.surface.applyTo

class AddPathSurface : OsmFilterQuestType<SurfaceOrIsStepsAnswer>() {

    override val elementFilter = """
        ways with highway ~ path|footway|cycleway|bridleway|steps
        and segregated != yes
        and access !~ private|no
        and (!conveying or conveying = no)
        and (!indoor or indoor = no)
        and (
          !surface
          or surface ~ ${ANYTHING_UNPAVED.joinToString("|")} and surface older today -6 years
          or surface older today -8 years
          or (
            surface ~ paved|unpaved|${INVALID_SURFACES.joinToString("|")}
            and !surface:note
            and !note:surface
          )
        )
        and ~path|footway|cycleway|bridleway !~ link
    """
    /* ~paved ways are less likely to change the surface type */

    override val changesetComment = "Specify path surfaces"
    override val wikiLink = "Key:surface"
    override val icon = R.drawable.ic_quest_way_surface
    override val achievements = listOf(PEDESTRIAN, WHEELCHAIR, BICYCLIST, OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_surface_title

    override fun createForm() = AddPathSurfaceForm()

    override fun applyAnswerTo(answer: SurfaceOrIsStepsAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            is SurfaceAnswer -> {
                answer.value.applyTo(tags)
            }
            is IsActuallyStepsAnswer -> {
                tags.changeToSteps()
            }
            is IsIndoorsAnswer -> {
                tags["indoor"] = "yes"
            }
        }
    }
}
