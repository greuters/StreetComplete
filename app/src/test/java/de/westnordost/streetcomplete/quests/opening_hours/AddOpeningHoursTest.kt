package de.westnordost.streetcompletegpx.quests.opening_hours

import ch.poole.openinghoursparser.Rule
import ch.poole.openinghoursparser.TimeSpan
import ch.poole.openinghoursparser.WeekDay
import ch.poole.openinghoursparser.WeekDayRange
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryAdd
import de.westnordost.streetcompletegpx.data.osm.edits.update_tags.StringMapEntryModify
import de.westnordost.streetcompletegpx.osm.nowAsCheckDateString
import de.westnordost.streetcompletegpx.osm.opening_hours.parser.OpeningHoursRuleList
import de.westnordost.streetcompletegpx.osm.toCheckDate
import de.westnordost.streetcompletegpx.quests.verifyAnswer
import de.westnordost.streetcompletegpx.testutils.mock
import de.westnordost.streetcompletegpx.testutils.node
import de.westnordost.streetcompletegpx.util.ktx.nowAsEpochMilliseconds
import de.westnordost.streetcompletegpx.util.ktx.toEpochMilli
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AddOpeningHoursTest {

    private val questType = AddOpeningHours(mock())

    @Test fun `apply description answer`() {
        questType.verifyAnswer(
            DescribeOpeningHours("my cool \"opening\" hours"),
            StringMapEntryAdd("opening_hours", "\"my cool opening hours\"")
        )
    }

    @Test fun `apply description answer when it already had an opening hours`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "my opening hours"),
            DescribeOpeningHours("my cool \"opening\" hours"),
            StringMapEntryModify("opening_hours", "my opening hours", "\"my cool opening hours\"")
        )
    }

    @Test fun `apply same description answer again`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "\"oh\""),
            DescribeOpeningHours("oh"),
            StringMapEntryModify("opening_hours", "\"oh\"", "\"oh\""),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply no opening hours sign answer`() {
        questType.verifyAnswer(
            NoOpeningHoursSign,
            StringMapEntryAdd("opening_hours:signed", "no"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply no opening hours sign answer when there was an answer before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "oh"),
            NoOpeningHoursSign,
            StringMapEntryAdd("opening_hours:signed", "no"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply no opening hours sign answer when there was an always open answer before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "24/7"),
            NoOpeningHoursSign,
            StringMapEntryAdd("opening_hours:signed", "no"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply always open answer`() {
        questType.verifyAnswer(
            AlwaysOpen,
            StringMapEntryAdd("opening_hours", "24/7")
        )
    }

    @Test fun `apply always open answer when there was a different answer before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "34/3"),
            AlwaysOpen,
            StringMapEntryModify("opening_hours", "34/3", "24/7")
        )
    }

    @Test fun `apply always open answer when it was the same answer before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "24/7"),
            AlwaysOpen,
            StringMapEntryModify("opening_hours", "24/7", "24/7"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply always open answer when it was explicitly signed before`() {
        questType.verifyAnswer(
            mapOf("opening_hours:signed" to "yes"),
            AlwaysOpen,
            StringMapEntryAdd("opening_hours", "24/7")
        )
    }

    @Test fun `apply always open answer when it was explicitly signed and present before`() {
        questType.verifyAnswer(
            mapOf(
                "opening_hours" to "24/7",
                "opening_hours:signed" to "yes"
            ),
            AlwaysOpen,
            StringMapEntryModify("opening_hours", "24/7", "24/7"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `apply always open answer when it was explicitly signed but there was a different answer before`() {
        questType.verifyAnswer(
            mapOf(
                "opening_hours" to "34/3",
                "opening_hours:signed" to "yes"
            ),
            AlwaysOpen,
            StringMapEntryModify("opening_hours", "34/3", "24/7")
        )
    }

    @Test fun `apply opening hours answer`() {
        questType.verifyAnswer(
            RegularOpeningHours(OpeningHoursRuleList(listOf(
                Rule().apply {
                    days = listOf(WeekDayRange().also {
                        it.startDay = WeekDay.MO
                    })
                    times = listOf(TimeSpan().also {
                        it.start = 60 * 10
                        it.end = 60 * 12
                    })
                },
                Rule().apply {
                    days = listOf(WeekDayRange().also {
                        it.startDay = WeekDay.TU
                    })
                    times = listOf(TimeSpan().also {
                        it.start = 60 * 12
                        it.end = 60 * 24
                    })
                })
            )),
            StringMapEntryAdd("opening_hours", "Mo 10:00-12:00; Tu 12:00-24:00")
        )
    }

    @Test fun `apply opening hours answer when there was a different one before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "hohoho"),
            RegularOpeningHours(OpeningHoursRuleList(listOf(
                Rule().apply {
                    days = listOf(WeekDayRange().also {
                        it.startDay = WeekDay.MO
                    })
                    times = listOf(TimeSpan().also {
                        it.start = 60 * 10
                        it.end = 60 * 12
                    })
                })
            )),
            StringMapEntryModify("opening_hours", "hohoho", "Mo 10:00-12:00")
        )
    }

    @Test fun `apply opening hours answer when there was the same one before`() {
        questType.verifyAnswer(
            mapOf("opening_hours" to "Mo 10:00-12:00"),
            RegularOpeningHours(OpeningHoursRuleList(listOf(
                Rule().apply {
                    days = listOf(WeekDayRange().also {
                        it.startDay = WeekDay.MO
                    })
                    times = listOf(TimeSpan().also {
                        it.start = 60 * 10
                        it.end = 60 * 12
                    })
                })
            )),
            StringMapEntryModify("opening_hours", "Mo 10:00-12:00", "Mo 10:00-12:00"),
            StringMapEntryAdd("check_date:opening_hours", nowAsCheckDateString())
        )
    }

    @Test fun `isApplicableTo returns false for unknown places`() {
        assertFalse(questType.isApplicableTo(node(
            tags = mapOf("whatisthis" to "something")
        )))
    }

    @Test fun `isApplicableTo returns true for known places`() {
        assertTrue(questType.isApplicableTo(node(
            tags = mapOf("shop" to "sports", "name" to "Atze's Angelladen")
        )))
    }

    @Test fun `isApplicableTo returns false for known places with recently edited opening hours`() {
        assertFalse(questType.isApplicableTo(
            node(tags = mapOf("shop" to "sports", "name" to "Atze's Angelladen", "opening_hours" to "Mo-Fr 10:00-20:00"), timestamp = nowAsEpochMilliseconds())
        ))
    }

    @Test fun `isApplicableTo returns true for known places with old opening hours`() {
        val milisecondsFor400Days: Long = 1000L * 60 * 60 * 24 * 400
        assertTrue(questType.isApplicableTo(
            node(tags = mapOf("shop" to "sports", "name" to "Atze's Angelladen", "opening_hours" to "Mo-Fr 10:00-20:00"), timestamp = nowAsEpochMilliseconds() - milisecondsFor400Days)
        ))
    }

    @Test fun `isApplicableTo returns false for closed shops with old opening hours`() {
        val milisecondsFor400Days: Long = 1000L * 60 * 60 * 24 * 400
        assertFalse(questType.isApplicableTo(
            node(tags = mapOf("nonexisting:shop" to "sports", "name" to "Atze's Angelladen", "opening_hours" to "Mo-Fr 10:00-20:00"), timestamp = nowAsEpochMilliseconds() - milisecondsFor400Days)
        ))
    }

    @Test fun `isApplicableTo returns true for parks with old opening hours`() {
        val milisecondsFor400Days: Long = 1000L * 60 * 60 * 24 * 400
        assertTrue(questType.isApplicableTo(
            node(tags = mapOf("leisure" to "park", "name" to "Trolololo", "opening_hours" to "Mo-Fr 10:00-20:00"), timestamp = nowAsEpochMilliseconds() - milisecondsFor400Days)
        ))
    }

    @Test fun `isApplicableTo returns false for toilets without opening hours`() {
        assertFalse(questType.isApplicableTo(
            node(tags = mapOf("amenity" to "toilets"), timestamp = nowAsEpochMilliseconds())
        ))
    }

    @Test fun `isApplicableTo returns true if the opening hours cannot be parsed`() {
        assertTrue(questType.isApplicableTo(node(
            tags = mapOf(
                "shop" to "supermarket",
                "name" to "Supi",
                "opening_hours" to "maybe open maybe closed who knows"
            ),
            timestamp = "2000-11-11".toCheckDate()?.toEpochMilli()
        )))
    }

    @Test fun `isApplicableTo returns false if the opening hours are not supported`() {
        assertFalse(questType.isApplicableTo(node(
            tags = mapOf(
                "shop" to "supermarket",
                "name" to "Supi",
                "opening_hours" to "1998 Mo-Fr 18:00-20:00"
            ),
            timestamp = "2000-11-11".toCheckDate()?.toEpochMilli()
        )))
    }

    @Test fun `isApplicableTo returns false if the opening hours are not signed`() {
        assertFalse(questType.isApplicableTo(node(
            tags = mapOf(
                "shop" to "supermarket",
                "name" to "Supi",
                "opening_hours:signed" to "no"
            ),
            timestamp = "2000-11-11".toCheckDate()?.toEpochMilli()
        )))
    }

    @Test fun `isApplicableTo returns false if the opening hours are not signed, even if there are actually some set`() {
        assertFalse(questType.isApplicableTo(node(
            tags = mapOf(
                "shop" to "supermarket",
                "name" to "Supi",
                "opening_hours" to "24/7",
                "opening_hours:signed" to "no"
            ),
            timestamp = "2000-11-11".toCheckDate()?.toEpochMilli()
        )))
    }
}
