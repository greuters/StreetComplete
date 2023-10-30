package de.westnordost.streetcompletegpx.quests.postbox_ref

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.QuestRefBinding
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.util.ktx.nonBlankTextOrNull

class AddPostboxRefForm : AbstractOsmQuestForm<PostboxRefAnswer>() {

    override val contentLayoutResId = R.layout.quest_ref
    private val binding by contentViewBinding(QuestRefBinding::bind)

    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_ref_answer_noRef) { confirmNoRef() }
    )

    private val ref get() = binding.refInput.nonBlankTextOrNull

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refInput.doAfterTextChanged { checkIsFormComplete() }
    }

    override fun onClickOk() {
        applyAnswer(PostboxRef(ref!!))
    }

    private fun confirmNoRef() {
        val ctx = context ?: return
        AlertDialog.Builder(ctx)
            .setTitle(R.string.quest_generic_confirmation_title)
            .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ -> applyAnswer(NoVisiblePostboxRef) }
            .setNegativeButton(R.string.quest_generic_confirmation_no, null)
            .show()
    }

    override fun isFormComplete() = ref != null
}
