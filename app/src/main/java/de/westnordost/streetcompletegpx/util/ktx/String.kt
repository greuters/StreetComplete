package de.westnordost.streetcompletegpx.util.ktx

fun String.truncate(length: Int): String =
    if (this.length > length) substring(0, length - 1) + "…" else this
