package de.westnordost.streetcompletegpx.quests.bus_stop_name

import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryAdd
import de.westnordost.streetcompletegpx.osm.LocalizedName
import de.westnordost.streetcompletegpx.quests.verifyAnswer
import kotlin.test.Test

class AddBusStopNameTest {

    private val questType = AddBusStopName()

    @Test
    fun `apply no name answer`() {
        questType.verifyAnswer(
            NoBusStopName,
            StringMapEntryAdd("name:signed", "no")
        )
    }

    @Test fun `apply name answer with one name`() {
        questType.verifyAnswer(
            BusStopName(listOf(LocalizedName("", "my name"))),
            StringMapEntryAdd("name", "my name")
        )
    }

    @Test fun `apply name answer with multiple names`() {
        questType.verifyAnswer(
            BusStopName(listOf(
                LocalizedName("", "Altona / All-Too-Close"),
                LocalizedName("de", "Altona"),
                LocalizedName("en", "All-Too-Close")
            )),
            StringMapEntryAdd("name", "Altona / All-Too-Close"),
            StringMapEntryAdd("name:en", "All-Too-Close"),
            StringMapEntryAdd("name:de", "Altona")
        )
    }
}
