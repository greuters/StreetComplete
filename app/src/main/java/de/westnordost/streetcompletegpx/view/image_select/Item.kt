package de.westnordost.streetcompletegpx.view.image_select

import de.westnordost.streetcompletegpx.view.Image
import de.westnordost.streetcompletegpx.view.ResImage
import de.westnordost.streetcompletegpx.view.ResText
import de.westnordost.streetcompletegpx.view.Text

data class Item<T>(
    override val value: T?,
    val drawableId: Int? = null,
    val titleId: Int? = null,
    val descriptionId: Int? = null,
    override val items: List<GroupableDisplayItem<T>>? = null
) : GroupableDisplayItem<T> {

    override val image: Image? get() = drawableId?.let { ResImage(it) }
    override val title: Text? get() = titleId?.let { ResText(titleId) }
    override val description: Text? get() = descriptionId?.let { ResText(descriptionId) }
}

data class Item2<T>(
    override val value: T?,
    override val image: Image? = null,
    override val title: Text? = null,
    override val description: Text? = null
) : DisplayItem<T>
