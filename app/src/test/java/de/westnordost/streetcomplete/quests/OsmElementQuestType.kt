package de.westnordost.streetcompletegpx.quests

import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapChangesBuilder
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryChange
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementPointGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmElementQuestType
import org.assertj.core.api.Assertions.assertThat

fun <T> OsmElementQuestType<T>.verifyAnswer(tags: Map<String, String>, answer: T, vararg expectedChanges: StringMapEntryChange) {
    val cb = StringMapChangesBuilder(tags)
    this.applyAnswerTo(answer, cb, ElementPointGeometry(LatLon(0.0, 0.0)), 0)
    val changes = cb.create().changes
    assertThat(changes).containsExactlyInAnyOrder(*expectedChanges)
}

fun <T> OsmElementQuestType<T>.verifyAnswer(answer: T, vararg expectedChanges: StringMapEntryChange) {
    verifyAnswer(emptyMap(), answer, *expectedChanges)
}
