package de.westnordost.streetcompletegpx.quests.crossing_island

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmElementQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.BLIND
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.isCrossing
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddCrossingIsland : OsmElementQuestType<Boolean> {

    private val crossingFilter by lazy { """
        nodes with
          highway = crossing
          and foot != no
          and crossing
          and crossing != island
          and !crossing:island
    """.toElementFilterExpression() }

    private val excludedWaysFilter by lazy { """
        ways with
          highway and access ~ private|no
          or railway
          or highway = service
          or highway and oneway and oneway != no
    """.toElementFilterExpression() }

    override val changesetComment = "Specify whether pedestrian crossings have islands"
    override val wikiLink = "Key:crossing:island"
    override val icon = R.drawable.ic_quest_pedestrian_crossing_island
    override val achievements = listOf(PEDESTRIAN, BLIND)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_pedestrian_crossing_island

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter { it.isCrossing() }.asSequence()

    override fun getApplicableElements(mapData: MapDataWithGeometry): Iterable<Element> {
        val excludedWayNodeIds = mapData.ways
            .filter { excludedWaysFilter.matches(it) }
            .flatMapTo(HashSet()) { it.nodeIds }

        return mapData.nodes
            .filter { crossingFilter.matches(it) && it.id !in excludedWayNodeIds }
    }

    override fun isApplicableTo(element: Element): Boolean? =
        if (!crossingFilter.matches(element)) false else null

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["crossing:island"] = answer.toYesNo()
    }
}
