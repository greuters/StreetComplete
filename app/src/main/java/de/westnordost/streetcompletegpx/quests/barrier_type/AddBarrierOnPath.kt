package de.westnordost.streetcompletegpx.quests.barrier_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Node
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmElementQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.PEDESTRIAN
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.WHEELCHAIR
import de.westnordost.streetcompletegpx.osm.ALL_PATHS
import de.westnordost.streetcompletegpx.osm.Tags

class AddBarrierOnPath : OsmElementQuestType<BarrierType> {

    private val barrierFilter by lazy { """
        ways with
          barrier ~ wall|fence|hedge|guard_rail|city_wall
          and area != yes
    """.toElementFilterExpression() }

    private val pathsFilter by lazy { """
        ways with
          (highway ~ ${ALL_PATHS.joinToString("|")} and area != yes)
          and (access !~ private|no or (foot and foot !~ private|no))
    """.toElementFilterExpression() }

    override val changesetComment = "Specify how paths and barriers intersect"
    override val wikiLink = "Key:barrier"
    override val icon = R.drawable.ic_quest_barrier_on_path
    override val achievements = listOf(PEDESTRIAN, WHEELCHAIR, OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_barrier_path_intersection

    override fun getApplicableElements(mapData: MapDataWithGeometry): Iterable<Element> =
        detectWayBarrierIntersection(mapData, barrierFilter, pathsFilter)

    override fun isApplicableTo(element: Element): Boolean? =
        if (element !is Node || element.tags.isNotEmpty()) false else null

    override fun createForm() = AddBarrierTypeForm()

    override fun applyAnswerTo(answer: BarrierType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) =
        answer.applyTo(tags)
}
