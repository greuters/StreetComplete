package de.westnordost.streetcompletegpx.quests.tourism_information

import android.os.Bundle
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm

class AddInformationForm : AImageListQuestForm<TourismInformation, TourismInformation>() {

    override val itemsPerRow = 2

    override val items = TourismInformation.values().map { it.asItem() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSelector.cellLayoutId = R.layout.cell_icon_select_with_label_below
    }

    override fun onClickOk(selectedItems: List<TourismInformation>) {
        applyAnswer(selectedItems.single())
    }
}
