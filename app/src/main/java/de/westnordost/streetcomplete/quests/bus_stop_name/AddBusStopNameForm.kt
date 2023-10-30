package de.westnordost.streetcompletegpx.quests.bus_stop_name

import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.QuestLocalizednameBinding
import de.westnordost.streetcompletegpx.osm.LocalizedName
import de.westnordost.streetcompletegpx.quests.AAddLocalizedNameForm
import de.westnordost.streetcompletegpx.quests.AnswerItem

class AddBusStopNameForm : AAddLocalizedNameForm<BusStopNameAnswer>() {

    override val contentLayoutResId = R.layout.quest_localizedname
    private val binding by contentViewBinding(QuestLocalizednameBinding::bind)

    override val addLanguageButton get() = binding.addLanguageButton
    override val namesList get() = binding.namesList

    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_placeName_no_name_answer) { confirmNoName() },
        AnswerItem(R.string.quest_streetName_answer_cantType) { showKeyboardInfo() }
    )

    override fun onClickOk(names: List<LocalizedName>) {
        applyAnswer(BusStopName(names))
    }

    private fun confirmNoName() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.quest_name_answer_noName_confirmation_title)
            .setPositiveButton(R.string.quest_name_noName_confirmation_positive) { _, _ -> applyAnswer(NoBusStopName) }
            .setNegativeButton(R.string.quest_generic_confirmation_no, null)
            .show()
    }
}
