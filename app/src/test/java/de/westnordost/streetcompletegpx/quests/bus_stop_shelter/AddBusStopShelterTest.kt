package de.westnordost.streetcompletegpx.quests.bus_stop_shelter

import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryAdd
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryDelete
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryModify
import de.westnordost.streetcompletegpx.osm.nowAsCheckDateString
import de.westnordost.streetcompletegpx.quests.verifyAnswer
import kotlin.test.Test

class AddBusStopShelterTest {

    private val questType = AddBusStopShelter()

    @Test fun `apply shelter yes answer`() {
        questType.verifyAnswer(
            BusStopShelterAnswer.SHELTER,
            StringMapEntryAdd("shelter", "yes")
        )
    }

    @Test fun `apply shelter yes again answer`() {
        questType.verifyAnswer(
            mapOf("shelter" to "yes"),
            BusStopShelterAnswer.SHELTER,
            StringMapEntryModify("shelter", "yes", "yes"),
            StringMapEntryAdd("check_date:shelter", nowAsCheckDateString())
        )
    }

    @Test fun `apply shelter no answer`() {
        questType.verifyAnswer(
            BusStopShelterAnswer.NO_SHELTER,
            StringMapEntryAdd("shelter", "no")
        )
    }

    @Test fun `apply shelter no again answer`() {
        questType.verifyAnswer(
            mapOf("shelter" to "no"),
            BusStopShelterAnswer.NO_SHELTER,
            StringMapEntryModify("shelter", "no", "no"),
            StringMapEntryAdd("check_date:shelter", nowAsCheckDateString())
        )
    }

    @Test fun `apply covered answer`() {
        questType.verifyAnswer(
            BusStopShelterAnswer.COVERED,
            StringMapEntryAdd("covered", "yes")
        )
    }

    @Test fun `apply covered when answer before was shelter answer`() {
        questType.verifyAnswer(
            mapOf("shelter" to "no"),
            BusStopShelterAnswer.COVERED,
            StringMapEntryAdd("covered", "yes"),
            StringMapEntryDelete("shelter", "no")
        )
    }
}
