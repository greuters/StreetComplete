package de.westnordost.streetcompletegpx.quests.place_name

import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryAdd
import de.westnordost.streetcompletegpx.osm.LocalizedName
import de.westnordost.streetcompletegpx.quests.verifyAnswer
import de.westnordost.streetcompletegpx.testutils.mock
import kotlin.test.Test

class AddPlaceNameTest {

    private val questType = AddPlaceName(mock())

    @Test fun `apply no name answer`() {
        questType.verifyAnswer(
            NoPlaceNameSign,
            StringMapEntryAdd("name:signed", "no")
        )
    }

    @Test fun `apply name answer`() {
        questType.verifyAnswer(
            PlaceName(listOf(
                LocalizedName("", "Hey ya!"),
                LocalizedName("de", "He ja!"),
            )),
            StringMapEntryAdd("name", "Hey ya!"),
            StringMapEntryAdd("name:de", "He ja!"),
        )
    }
}
