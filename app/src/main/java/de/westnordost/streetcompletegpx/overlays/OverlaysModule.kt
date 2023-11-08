package de.westnordost.streetcompletegpx.overlays

import de.westnordost.countryboundaries.CountryBoundaries
import de.westnordost.osmfeatures.Feature
import de.westnordost.osmfeatures.FeatureDictionary
import de.westnordost.streetcompletegpx.data.meta.CountryInfos
import de.westnordost.streetcompletegpx.data.overlays.OverlayRegistry
import de.westnordost.streetcompletegpx.overlays.address.AddressOverlay
import de.westnordost.streetcompletegpx.overlays.cycleway.CyclewayOverlay
import de.westnordost.streetcompletegpx.overlays.shops.ShopsOverlay
import de.westnordost.streetcompletegpx.overlays.sidewalk.SidewalkOverlay
import de.westnordost.streetcompletegpx.overlays.street_parking.StreetParkingOverlay
import de.westnordost.streetcompletegpx.overlays.surface.SurfaceOverlay
import de.westnordost.streetcompletegpx.overlays.way_lit.WayLitOverlay
import de.westnordost.streetcompletegpx.util.ktx.getFeature
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.FutureTask

/* Each overlay is assigned an ordinal. This is used for serialization and is thus never changed,
*  even if the order of overlays is changed.  */
val overlaysModule = module {
    single {
        overlaysRegistry(
            get(),
            get(named("CountryBoundariesFuture")),
            { tags ->
                get<FutureTask<FeatureDictionary>>(named("FeatureDictionaryFuture"))
                .get().getFeature(tags)
            }
        )
    }
}

fun overlaysRegistry(
    countryInfos: CountryInfos,
    countryBoundariesFuture: FutureTask<CountryBoundaries>,
    getFeature: (tags: Map<String, String>) -> Feature?,
) = OverlayRegistry(listOf(

    0 to WayLitOverlay(),
    6 to SurfaceOverlay(),
    1 to SidewalkOverlay(),
    5 to CyclewayOverlay(countryInfos, countryBoundariesFuture),
    2 to StreetParkingOverlay(),
    3 to AddressOverlay(countryBoundariesFuture),
    4 to ShopsOverlay(getFeature),
))
