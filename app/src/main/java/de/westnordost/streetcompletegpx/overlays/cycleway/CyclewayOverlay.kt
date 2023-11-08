package de.westnordost.streetcompletegpx.overlays.cycleway

import de.westnordost.countryboundaries.CountryBoundaries
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.meta.CountryInfo
import de.westnordost.streetcompletegpx.data.meta.CountryInfos
import de.westnordost.streetcompletegpx.data.meta.getByLocation
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement
import de.westnordost.streetcompletegpx.osm.ALL_ROADS
import de.westnordost.streetcompletegpx.osm.MAXSPEED_TYPE_KEYS
import de.westnordost.streetcompletegpx.osm.bicycle_boulevard.BicycleBoulevard
import de.westnordost.streetcompletegpx.osm.bicycle_boulevard.createBicycleBoulevard
import de.westnordost.streetcompletegpx.osm.cycleway.Cycleway
import de.westnordost.streetcompletegpx.osm.cycleway.Cycleway.*
import de.westnordost.streetcompletegpx.osm.cycleway.createCyclewaySides
import de.westnordost.streetcompletegpx.osm.cycleway.isAmbiguous
import de.westnordost.streetcompletegpx.osm.cycleway_separate.SeparateCycleway
import de.westnordost.streetcompletegpx.osm.cycleway_separate.createSeparateCycleway
import de.westnordost.streetcompletegpx.osm.isPrivateOnFoot
import de.westnordost.streetcompletegpx.osm.surface.ANYTHING_UNPAVED
import de.westnordost.streetcompletegpx.overlays.Color
import de.westnordost.streetcompletegpx.overlays.Overlay
import de.westnordost.streetcompletegpx.overlays.PolylineStyle
import de.westnordost.streetcompletegpx.overlays.StrokeStyle
import de.westnordost.streetcompletegpx.quests.cycleway.AddCycleway
import java.util.concurrent.FutureTask

class CyclewayOverlay(
    private val countryInfos: CountryInfos,
    private val countryBoundaries: FutureTask<CountryBoundaries>
) : Overlay {

    override val title = R.string.overlay_cycleway
    override val icon = R.drawable.ic_quest_bicycleway
    override val changesetComment = "Specify whether there are cycleways"
    override val wikiLink: String = "Key:cycleway"
    override val achievements = listOf(EditTypeAchievement.BICYCLIST)
    override val hidesQuestTypes = setOf(AddCycleway::class.simpleName!!)

    override fun getStyledElements(mapData: MapDataWithGeometry) =
        // roads
        mapData.filter("""
            ways with
              highway ~ ${ALL_ROADS.joinToString("|")}
              and area != yes
        """).mapNotNull {
            val pos = mapData.getWayGeometry(it.id)?.center ?: return@mapNotNull null
            val countryInfo = countryInfos.getByLocation(
                countryBoundaries.get(),
                pos.longitude,
                pos.latitude
            )
            it to getStreetCyclewayStyle(it, countryInfo)
        } +
        // separately mapped ways
        mapData.filter("""
            ways with
              highway ~ cycleway|path|footway
              and horse != designated
              and area != yes
        """).map { it to getSeparateCyclewayStyle(it) }

    override fun createForm(element: Element?) =
        if (element == null) null
        else if (element.tags["highway"] in ALL_ROADS) StreetCyclewayOverlayForm()
        else SeparateCyclewayForm()
}

private fun getSeparateCyclewayStyle(element: Element) =
    PolylineStyle(StrokeStyle(createSeparateCycleway(element.tags).getColor()))

private fun SeparateCycleway?.getColor() = when (this) {
    SeparateCycleway.NOT_ALLOWED,
    SeparateCycleway.ALLOWED_ON_FOOTWAY,
    SeparateCycleway.NON_DESIGNATED,
    SeparateCycleway.PATH ->
        Color.BLACK

    SeparateCycleway.NON_SEGREGATED ->
        Color.CYAN

    SeparateCycleway.SEGREGATED,
    SeparateCycleway.EXCLUSIVE,
    SeparateCycleway.EXCLUSIVE_WITH_SIDEWALK ->
        Color.BLUE

    null ->
        Color.INVISIBLE
}

private fun getStreetCyclewayStyle(element: Element, countryInfo: CountryInfo): PolylineStyle {
    val cycleways = createCyclewaySides(element.tags, countryInfo.isLeftHandTraffic)
    val isBicycleBoulevard = createBicycleBoulevard(element.tags) == BicycleBoulevard.YES

    // not set but on road that usually has no cycleway or it is private -> do not highlight as missing
    val isNoCyclewayExpected =
        cycleways == null && (cyclewayTaggingNotExpected(element) || isPrivateOnFoot(element))

    return PolylineStyle(
        stroke = when {
            isBicycleBoulevard ->   StrokeStyle(Color.GOLD, dashed = true)
            isNoCyclewayExpected -> StrokeStyle(Color.INVISIBLE)
            else ->                 null
        },
        strokeLeft = if (isNoCyclewayExpected) null else cycleways?.left?.cycleway.getStyle(countryInfo),
        strokeRight = if (isNoCyclewayExpected) null else cycleways?.right?.cycleway.getStyle(countryInfo)
    )
}

private val cyclewayTaggingNotExpectedFilter by lazy { """
    ways with
      highway ~ track|living_street|pedestrian|service|motorway_link|motorway
      or motorroad = yes
      or expressway = yes
      or maxspeed <= 20
      or cyclestreet = yes
      or bicycle_road = yes
      or surface ~ ${ANYTHING_UNPAVED.joinToString("|")}
      or ~"${(MAXSPEED_TYPE_KEYS + "maxspeed").joinToString("|")}" ~ ".*:(zone)?:?([1-9]|[1-2][0-9]|30)"
""".toElementFilterExpression() }

private fun cyclewayTaggingNotExpected(element: Element) =
    cyclewayTaggingNotExpectedFilter.matches(element)

private fun Cycleway?.getStyle(countryInfo: CountryInfo) = when (this) {
    TRACK ->
        StrokeStyle(Color.BLUE)

    EXCLUSIVE_LANE, UNSPECIFIED_LANE ->
        if (isAmbiguous(countryInfo)) StrokeStyle(Color.DATA_REQUESTED)
        else                          StrokeStyle(Color.GOLD)

    ADVISORY_LANE, SUGGESTION_LANE, UNSPECIFIED_SHARED_LANE ->
        if (isAmbiguous(countryInfo)) StrokeStyle(Color.DATA_REQUESTED)
        else                          StrokeStyle(Color.ORANGE)

    PICTOGRAMS ->
        StrokeStyle(Color.ORANGE, dashed = true)

    UNKNOWN, INVALID, null, UNKNOWN_LANE, UNKNOWN_SHARED_LANE ->
        StrokeStyle(Color.DATA_REQUESTED)

    BUSWAY ->
        StrokeStyle(Color.LIME, dashed = true)

    SIDEWALK_EXPLICIT ->
        StrokeStyle(Color.CYAN, dashed = true)

    NONE ->
        StrokeStyle(Color.BLACK)

    SHOULDER, NONE_NO_ONEWAY ->
        StrokeStyle(Color.BLACK, dashed = true)

    SEPARATE ->
        StrokeStyle(Color.INVISIBLE)
}
