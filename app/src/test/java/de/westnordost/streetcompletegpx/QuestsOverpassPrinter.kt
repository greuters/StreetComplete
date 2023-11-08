package de.westnordost.streetcompletegpx

import de.westnordost.streetcompletegpx.data.elementfilter.toOverpassQLString
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmElementQuestType
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcompletegpx.quests.questTypeRegistry
import de.westnordost.streetcompletegpx.testutils.mock

fun main() {

    val registry = questTypeRegistry(mock(), mock(), mock(), mock(), mock(), mock())

    for (questType in registry) {
        if (questType is OsmElementQuestType<*>) {
            println("### " + questType.name)
            if (questType is OsmFilterQuestType<*>) {
                val query = "[bbox:{{bbox}}];\n" + questType.filter.toOverpassQLString() + "\n out meta geom;"
                println("```\n$query\n```")
            } else {
                println("Not available, see source code")
            }
            println()
        }
    }
}
