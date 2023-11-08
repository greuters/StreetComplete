package de.westnordost.streetcompletegpx.data.overlays

import de.westnordost.streetcompletegpx.data.ObjectTypeRegistry
import de.westnordost.streetcompletegpx.overlays.Overlay

/** Every overlay must be registered here
 *
 * Could theoretically be done with Reflection, but that doesn't really work on Android.
 *
 * It is also used to assign each overlay an ordinal for serialization.
 */
class OverlayRegistry(ordinalsAndEntries: List<Pair<Int, Overlay>>) : ObjectTypeRegistry<Overlay>(ordinalsAndEntries)
