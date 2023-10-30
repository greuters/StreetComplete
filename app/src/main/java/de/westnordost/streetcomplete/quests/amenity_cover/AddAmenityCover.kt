package de.westnordost.streetcompletegpx.quests.amenity_cover

import de.westnordost.osmfeatures.Feature
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.elementfilter.toElementFilterExpression
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmElementQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.OUTDOORS
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddAmenityCover (
    private val getFeature: (tags: Map<String, String>) -> Feature?
) : OsmElementQuestType<Boolean> {

    private val nodesFilter by lazy { """
        nodes with
          (leisure = picnic_table
           or amenity = bbq)
          and access !~ private|no
          and !covered
          and (!seasonal or seasonal = no)
    """.toElementFilterExpression() }
    override val changesetComment = "Specify whether various amenities are covered"
    override val wikiLink = "Key:covered"
    override val icon = R.drawable.ic_quest_picnic_table_cover
    override val isDeleteElementEnabled = true
    override val achievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_amenityCover_title
    override fun getApplicableElements(mapData: MapDataWithGeometry): Iterable<Element> =
        mapData.filter { isApplicableTo(it) }

    override fun isApplicableTo(element: Element) =
        nodesFilter.matches(element) && hasAnyName(element.tags)

    private fun hasAnyName(tags: Map<String, String>) = getFeature(tags) != null

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry): Sequence<Element> {
        /* put markers for objects that are exactly the same as for which this quest is asking for
           e.g. it's a ticket validator? -> display other ticket validators. Etc. */
        val feature = getFeature(element.tags) ?: return emptySequence()

        return getMapData().filter { it.tags.containsAll(feature.tags) }.asSequence()
    }

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["covered"] = answer.toYesNo()
    }
}

private fun <X, Y> Map<X, Y>.containsAll(other: Map<X, Y>) = other.all { this[it.key] == it.value }
