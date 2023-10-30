package de.westnordost.streetcompletegpx.quests.incline_direction

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementPolylinesGeometry
import de.westnordost.streetcompletegpx.quests.AImageListQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.util.math.getOrientationAtCenterLineInDegrees
import kotlin.math.PI

class AddBicycleInclineForm : AImageListQuestForm<Incline, BicycleInclineAnswer>() {
    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_bicycle_incline_up_and_down) { confirmUpAndDown() }
    )

    override val items get() =
        Incline.values().map { it.asItem(requireContext(), wayRotation + mapRotation) }

    override val itemsPerRow = 2

    private var mapRotation: Float = 0f
    private var wayRotation: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wayRotation = (geometry as ElementPolylinesGeometry).getOrientationAtCenterLineInDegrees()
        imageSelector.cellLayoutId = R.layout.cell_icon_select_with_label_below
    }

    override fun onMapOrientation(rotation: Float, tilt: Float) {
        mapRotation = (rotation * 180 / PI).toFloat()
        imageSelector.items = items
    }

    private fun confirmUpAndDown() {
        val ctx = context ?: return
        AlertDialog.Builder(ctx)
            .setTitle(R.string.quest_generic_confirmation_title)
            .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ -> applyAnswer(
                UpdAndDownHopsAnswer) }
            .setNegativeButton(R.string.quest_generic_confirmation_no, null)
            .show()
    }

    override fun onClickOk(selectedItems: List<Incline>) {
        applyAnswer(RegularBicycleInclineAnswer(selectedItems.first()))
    }
}
