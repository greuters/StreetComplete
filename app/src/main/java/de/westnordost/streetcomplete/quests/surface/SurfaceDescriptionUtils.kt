package de.westnordost.streetcompletegpx.quests.surface

import android.content.Context
import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.osm.surface.Surface
import de.westnordost.streetcompletegpx.osm.surface.shouldBeDescribed

fun collectSurfaceDescriptionIfNecessary(
    context: Context,
    surface: Surface,
    onDescribed: (description: String?) -> Unit
) {
    if (!surface.shouldBeDescribed) {
        onDescribed(null)
    } else {
        AlertDialog.Builder(context)
            .setMessage(R.string.quest_surface_detailed_answer_impossible_confirmation)
            .setPositiveButton(R.string.quest_generic_confirmation_yes) { _, _ ->
                DescribeGenericSurfaceDialog(context, onDescribed).show()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}
