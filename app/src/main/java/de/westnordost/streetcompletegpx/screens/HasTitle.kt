package de.westnordost.streetcompletegpx.screens

interface HasTitle {
    val title: String
    val subtitle: String? get() = null
}
