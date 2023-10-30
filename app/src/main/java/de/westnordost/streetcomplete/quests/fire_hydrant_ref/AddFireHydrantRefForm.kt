package de.westnordost.streetcompletegpx.quests.fire_hydrant_ref

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.QuestRefBinding
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.util.ktx.nonBlankTextOrNull

class AddFireHydrantRefForm : AbstractOsmQuestForm<FireHydrantRefAnswer>() {

    override val contentLayoutResId = R.layout.quest_ref
    private val binding by contentViewBinding(QuestRefBinding::bind)

    override val otherAnswers get() = listOfNotNull(
        AnswerItem(R.string.quest_ref_answer_noRef) { confirmNoRef() },
    )

    private val ref get() = binding.refInput.nonBlankTextOrNull

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refInput.doAfterTextChanged { checkIsFormComplete() }
    }

    override fun onClickOk() {
        applyAnswer(FireHydrantRef(ref!!))
    }

    private fun confirmNoRef() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.quest_generic_confirmation_title)
            .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ -> applyAnswer(NoVisibleFireHydrantRef) }
            .setNegativeButton(R.string.quest_generic_confirmation_no, null)
            .show()
    }

    override fun isFormComplete() = ref != null
}
