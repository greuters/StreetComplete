package de.westnordost.streetcompletegpx.quests.camera_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.camera_type.CameraType.DOME
import de.westnordost.streetcompletegpx.quests.camera_type.CameraType.FIXED
import de.westnordost.streetcompletegpx.quests.camera_type.CameraType.PANNING
import de.westnordost.streetcompletegpx.view.image_select.Item

fun CameraType.asItem() = Item(this, iconResId, titleResId)

private val CameraType.titleResId: Int get() = when (this) {
    DOME ->    R.string.quest_camera_type_dome
    FIXED ->   R.string.quest_camera_type_fixed
    PANNING -> R.string.quest_camera_type_panning
}

private val CameraType.iconResId: Int get() = when (this) {
    DOME ->    R.drawable.ic_camera_type_dome
    FIXED ->   R.drawable.ic_camera_type_fixed
    PANNING -> R.drawable.ic_camera_type_panning
}
