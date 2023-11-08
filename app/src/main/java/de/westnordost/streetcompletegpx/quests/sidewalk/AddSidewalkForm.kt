package de.westnordost.streetcompletegpx.quests.sidewalk

import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.osm.sidewalk.LeftAndRightSidewalk
import de.westnordost.streetcompletegpx.osm.sidewalk.Sidewalk
import de.westnordost.streetcompletegpx.osm.sidewalk.Sidewalk.NO
import de.westnordost.streetcompletegpx.osm.sidewalk.Sidewalk.SEPARATE
import de.westnordost.streetcompletegpx.osm.sidewalk.Sidewalk.YES
import de.westnordost.streetcompletegpx.osm.sidewalk.asItem
import de.westnordost.streetcompletegpx.osm.sidewalk.asStreetSideItem
import de.westnordost.streetcompletegpx.quests.AStreetSideSelectForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.view.image_select.ImageListPickerDialog

class AddSidewalkForm : AStreetSideSelectForm<Sidewalk, LeftAndRightSidewalk>() {

    override val otherAnswers: List<AnswerItem> = listOf(
        AnswerItem(R.string.quest_sidewalk_answer_none) { noSidewalksHereHint() }
    )

    private fun noSidewalksHereHint() {
        activity?.let { AlertDialog.Builder(it)
            .setTitle(R.string.quest_sidewalk_answer_none_title)
            .setMessage(R.string.quest_side_select_interface_explanation)
            .setPositiveButton(android.R.string.ok, null)
            .show()
        }
    }

    override fun onClickSide(isRight: Boolean) {
        val items = listOf(YES, NO, SEPARATE).mapNotNull { it.asItem() }
        ImageListPickerDialog(requireContext(), items, R.layout.cell_icon_select_with_label_below, 2) { item ->
            streetSideSelect.replacePuzzleSide(item.value!!.asStreetSideItem()!!, isRight)
        }.show()
    }

    override fun onClickOk() {
        streetSideSelect.saveLastSelection()
        applyAnswer(LeftAndRightSidewalk(streetSideSelect.left?.value, streetSideSelect.right?.value))
    }

    override fun serialize(item: Sidewalk) = item.name
    override fun deserialize(str: String) = Sidewalk.valueOf(str)
    override fun asStreetSideItem(item: Sidewalk, isRight: Boolean) = item.asStreetSideItem()!!
}
