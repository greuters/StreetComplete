package de.westnordost.streetcompletegpx.quests.defibrillator

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.LIFESAVER
import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.YesNoQuestForm
import de.westnordost.streetcompletegpx.util.ktx.toYesNo

class AddIsDefibrillatorIndoor : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes with
         emergency = defibrillator
         and access !~ private|no
         and !indoor and !location
    """
    override val changesetComment = "Determine whether defibrillators are inside buildings"
    override val wikiLink = "Key:indoor"
    override val icon = R.drawable.ic_quest_defibrillator
    override val achievements = listOf(LIFESAVER)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_is_defibrillator_inside_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes with emergency = defibrillator")

    override fun createForm() = YesNoQuestForm()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["indoor"] = answer.toYesNo()
    }
}
