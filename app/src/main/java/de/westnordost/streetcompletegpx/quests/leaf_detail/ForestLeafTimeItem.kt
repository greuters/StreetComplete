package de.westnordost.streetcompletegpx.quests.leaf_detail

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.leaf_detail.ForestLeafType.BROADLEAVED
import de.westnordost.streetcompletegpx.quests.leaf_detail.ForestLeafType.MIXED
import de.westnordost.streetcompletegpx.quests.leaf_detail.ForestLeafType.NEEDLELEAVED
import de.westnordost.streetcompletegpx.view.image_select.Item

fun ForestLeafType.asItem() = Item(this, iconResId, titleResId)

private val ForestLeafType.titleResId: Int get() = when (this) {
    NEEDLELEAVED -> R.string.quest_leaf_type_needles
    BROADLEAVED ->  R.string.quest_leaf_type_broadleaved
    MIXED ->        R.string.quest_leaf_type_mixed
}

private val ForestLeafType.iconResId: Int get() = when (this) {
    NEEDLELEAVED -> R.drawable.leaf_type_needleleaved
    BROADLEAVED ->  R.drawable.leaf_type_broadleaved
    MIXED ->        R.drawable.leaf_type_mixed
}
