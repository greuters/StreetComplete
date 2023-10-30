package de.westnordost.streetcompletegpx.quests.bike_parking_capacity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.QuestBikeParkingCapacityBinding
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.util.ktx.intOrNull

class AddBikeParkingCapacityForm : AbstractOsmQuestForm<Int>() {

    override val contentLayoutResId = R.layout.quest_bike_parking_capacity
    private val binding by contentViewBinding(QuestBikeParkingCapacityBinding::bind)

    private val capacity get() = binding.capacityInput.intOrNull ?: 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showClarificationText = arguments?.getBoolean(ARG_SHOW_CLARIFICATION) ?: false
        binding.clarificationText.isGone = !showClarificationText
        binding.capacityInput.doAfterTextChanged { checkIsFormComplete() }
    }

    override fun isFormComplete() = capacity > 0

    override fun onClickOk() {
        applyAnswer(capacity)
    }

    companion object {
        private const val ARG_SHOW_CLARIFICATION = "show_clarification"

        fun create(showClarificationText: Boolean): AddBikeParkingCapacityForm {
            val form = AddBikeParkingCapacityForm()
            form.arguments = bundleOf(ARG_SHOW_CLARIFICATION to showClarificationText)
            return form
        }
    }
}
