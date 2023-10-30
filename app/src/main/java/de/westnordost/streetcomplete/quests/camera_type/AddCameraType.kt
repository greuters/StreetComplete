package de.westnordost.streetcompletegpx.quests.camera_type

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.Element
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.filter
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement.CITIZEN
import de.westnordost.streetcompletegpx.osm.Tags

class AddCameraType : OsmFilterQuestType<CameraType>() {

    override val elementFilter = """
        nodes with
         surveillance:type = camera
         and surveillance ~ public|outdoor|traffic
         and !camera:type
    """
    override val changesetComment = "Specify camera types"
    override val wikiLink = "Tag:surveillance:type"
    override val icon = R.drawable.ic_quest_surveillance_camera
    override val achievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_camera_type_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes with surveillance and surveillance:type = camera")

    override fun createForm() = AddCameraTypeForm()

    override fun applyAnswerTo(answer: CameraType, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["camera:type"] = answer.osmValue
    }
}
