package de.westnordost.streetcompletegpx.quests.foot

import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.DialogLivingStreetConfirmationBinding
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.foot.ProhibitedForPedestriansAnswer.HAS_SEPARATE_SIDEWALK
import de.westnordost.streetcompletegpx.quests.foot.ProhibitedForPedestriansAnswer.IS_LIVING_STREET
import de.westnordost.streetcompletegpx.quests.foot.ProhibitedForPedestriansAnswer.NO
import de.westnordost.streetcompletegpx.quests.foot.ProhibitedForPedestriansAnswer.YES
import de.westnordost.streetcompletegpx.util.ktx.livingStreetSignDrawableResId

class AddProhibitedForPedestriansForm : AbstractOsmQuestForm<ProhibitedForPedestriansAnswer>() {

    override val contentLayoutResId = R.layout.quest_prohibited_for_pedestrians_separate_sidewalk_explanation

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(YES) },
        AnswerItem(R.string.quest_sidewalk_value_yes) { applyAnswer(HAS_SEPARATE_SIDEWALK) }
    )

    // the living street answer stuff is copied from AddMaxSpeedForm
    override val otherAnswers: List<AnswerItem> get() {
        val result = mutableListOf<AnswerItem>()

        val highwayTag = element.tags["highway"]!!
        if (countryInfo.hasLivingStreet && MAYBE_LIVING_STREET.contains(highwayTag)) {
            result.add(AnswerItem(R.string.quest_maxspeed_answer_living_street) { confirmLivingStreet() })
        }
        return result
    }

    private fun confirmLivingStreet() {
        val ctx = context ?: return
        val dialogBinding = DialogLivingStreetConfirmationBinding.inflate(layoutInflater)
        countryInfo.livingStreetSignDrawableResId?.let { dialogBinding.livingStreetImage.setImageResource(it) }
        AlertDialog.Builder(ctx)
            .setView(dialogBinding.root)
            .setTitle(R.string.quest_maxspeed_answer_living_street_confirmation_title)
            .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ -> applyAnswer(IS_LIVING_STREET) }
            .setNegativeButton(R.string.quest_generic_confirmation_no, null)
            .show()
    }

    companion object {
        private val MAYBE_LIVING_STREET = listOf("residential", "unclassified")
    }
}
