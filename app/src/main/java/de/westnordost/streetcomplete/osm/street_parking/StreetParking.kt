package de.westnordost.streetcomplete.osm.street_parking

import de.westnordost.streetcomplete.osm.street_parking.ParkingOrientation.*
import de.westnordost.streetcomplete.osm.street_parking.ParkingPosition.*
import kotlinx.serialization.Serializable

data class LeftAndRightStreetParking(val left: StreetParking?, val right: StreetParking?)

@Serializable sealed class StreetParking

@Serializable object NoStreetParking : StreetParking()
/** When an unknown/unsupported value has been used */
@Serializable object UnknownStreetParking : StreetParking()
/** When not both parking orientation and position have been specified*/
@Serializable object IncompleteStreetParking : StreetParking()
/** There is street parking, but it is mapped as separate geometry */
@Serializable object StreetParkingSeparate : StreetParking()

@Serializable data class StreetParkingPositionAndOrientation(
    val orientation: ParkingOrientation,
    val position: ParkingPosition
) : StreetParking()

enum class ParkingOrientation {
    PARALLEL, DIAGONAL, PERPENDICULAR
}

enum class ParkingPosition {
    ON_STREET,
    HALF_ON_KERB,
    ON_KERB,
    STREET_SIDE,
    PAINTED_AREA_ONLY,
    SHOULDER
}

fun LeftAndRightStreetParking.validOrNullValues(): LeftAndRightStreetParking {
    if (left?.isValid != false && right?.isValid != false) return this
    return LeftAndRightStreetParking(left?.takeIf { it.isValid }, right?.takeIf { it.isValid })
}

private val StreetParking.isValid: Boolean get() = when (this) {
    IncompleteStreetParking, UnknownStreetParking -> false
    else -> true
}

val StreetParking.estimatedWidthOnRoad: Float get() = when (this) {
    is StreetParkingPositionAndOrientation ->
        estimatedReservedWidth * position.estimatedWidthOnRoadFactor
    else -> 0f // otherwise let's assume it's not on the street itself
}

val StreetParking.estimatedWidthOffRoad: Float get() = when (this) {
    is StreetParkingPositionAndOrientation ->
        estimatedReservedWidth * (1 - position.estimatedWidthOnRoadFactor)
    else -> 0f // otherwise let's assume it's not on the street itself
}

private val ParkingOrientation.estimatedWidth: Float get() = when (this) {
    PARALLEL -> 2f
    DIAGONAL -> 3f
    PERPENDICULAR -> 4f
}

private val StreetParkingPositionAndOrientation.estimatedReservedWidth: Float get() =
    /* we assume that in the case that cars are (merely) allowed to use the breakdown lane for
       parking, that also shoulder=* is set. So, the reserved width for parking is 0 as otherwise
       we would count the width of the shoulder twice.
     */
    if (position == SHOULDER) 0f
    else orientation.estimatedWidth

private val ParkingPosition.estimatedWidthOnRoadFactor: Float get() = when (this) {
    ON_STREET -> 1f
    HALF_ON_KERB -> 0.5f
    ON_KERB -> 0f
    else -> 0.5f // otherwise let's assume it is somehow on the street
}
