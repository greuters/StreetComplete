package de.westnordost.streetcompletegpx.quests.tactile_paving

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BLIND
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.surface.ANYTHING_PAVED
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

class AddTactilePavingSteps : OsmFilterQuestType<TactilePavingStepsAnswer>() {

    override val elementFilter = """
        ways with highway = steps
         and surface ~ ${ANYTHING_PAVED.joinToString("|")}
         and (!conveying or conveying = no)
         and access !~ private|no
        and (
          !tactile_paving
          or tactile_paving = unknown
          or tactile_paving ~ no|partial|incorrect and tactile_paving older today -4 years
          or tactile_paving = yes and tactile_paving older today -8 years
        )
    """

    override val changesetComment = "Survey tactile paving on steps"
    override val wikiLink = "Key:tactile_paving"
    override val icon = R.drawable.ic_quest_steps_tactile_paving
    override val enabledInCountries = COUNTRIES_WHERE_TACTILE_PAVING_IS_COMMON
    override val achievements = listOf(BLIND)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_tactilePaving_title_steps

    override fun createForm() = TactilePavingStepsForm()

    override fun applyAnswerTo(answer: TactilePavingStepsAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags.updateWithCheckDate("tactile_paving", answer.osmValue)
    }
}
