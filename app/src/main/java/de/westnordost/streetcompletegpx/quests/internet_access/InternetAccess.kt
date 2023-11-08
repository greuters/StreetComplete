package de.westnordost.streetcompletegpx.quests.internet_access

enum class InternetAccess(val osmValue: String) {
    WIFI("wlan"),
    NO("no"),
    TERMINAL("terminal"),
    WIRED("wired"),
}
