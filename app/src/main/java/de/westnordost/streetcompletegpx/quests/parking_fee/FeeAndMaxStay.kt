package de.westnordost.streetcompletegpx.quests.parking_fee

import de.westnordost.streetcompletegpx.osm.Tags

data class FeeAndMaxStay(val fee: Fee, val maxstay: Maxstay? = null)

fun FeeAndMaxStay.applyTo(tags: Tags) {
    fee.applyTo(tags)
    maxstay?.applyTo(tags)
}
