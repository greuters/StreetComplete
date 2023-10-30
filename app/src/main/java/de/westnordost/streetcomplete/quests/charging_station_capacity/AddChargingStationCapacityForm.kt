package de.westnordost.streetcompletegpx.quests.charging_station_capacity

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.databinding.QuestChargingStationCapacityBinding
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.util.ktx.intOrNull

class AddChargingStationCapacityForm : AbstractOsmQuestForm<Int>() {

    override val contentLayoutResId = R.layout.quest_charging_station_capacity
    private val binding by contentViewBinding(QuestChargingStationCapacityBinding::bind)

    private val capacity get() = binding.capacityInput.intOrNull ?: 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.capacityInput.doAfterTextChanged { checkIsFormComplete() }
    }

    override fun isFormComplete() = capacity > 0

    override fun onClickOk() {
        applyAnswer(capacity)
    }
}
