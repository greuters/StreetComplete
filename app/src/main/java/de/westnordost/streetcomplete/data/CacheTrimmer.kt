package de.westnordost.streetcompletegpx.data

import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataController
import de.westnordost.streetcompletegpx.data.quest.VisibleQuestsSource

class CacheTrimmer(
    private val visibleQuestsSource: VisibleQuestsSource,
    private val mapDataController: MapDataController,
) {
    fun trimCaches() {
        mapDataController.trimCache()
        visibleQuestsSource.trimCache()
    }

    fun clearCaches() {
        mapDataController.clearCache()
        visibleQuestsSource.clearCache()
    }
}
