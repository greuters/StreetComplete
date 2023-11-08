package de.westnordost.streetcompletegpx.data.overlay

import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement
import de.westnordost.streetcompletegpx.overlays.AbstractOverlayForm
import de.westnordost.streetcompletegpx.overlays.Overlay
import de.westnordost.streetcompletegpx.overlays.Style

open class TestOverlayA : Overlay {
    override fun getStyledElements(mapData: MapDataWithGeometry): Sequence<Pair<Element, Style>> = sequenceOf()
    override fun createForm(element: Element?): AbstractOverlayForm? = null
    override val changesetComment: String = "test"
    override val icon: Int = 0
    override val title: Int = 0
    override val wikiLink: String? = null
    override val achievements: List<EditTypeAchievement> = emptyList()
}

class TestOverlayB : TestOverlayA()
class TestOverlayC : TestOverlayA()
