package de.westnordost.streetcompletegpx.quests.roof_shape

import android.os.Bundle
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.roof_shape.RoofShape.MANY

class AddRoofShapeForm : AImageListQuestForm<RoofShape, RoofShape>() {

    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_roofShape_answer_many) { applyAnswer(MANY) }
    )

    override val items = RoofShape.values().mapNotNull { it.asItem() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSelector.cellLayoutId = R.layout.cell_labeled_icon_select
    }

    override fun onClickOk(selectedItems: List<RoofShape>) {
        applyAnswer(selectedItems.single())
    }
}
