package de.westnordost.streetcompletegpx.util

import android.os.Build

fun getDefaultTheme(): String = when {
    Build.VERSION.SDK_INT <= Build.VERSION_CODES.R -> "AUTO"
    else -> "SYSTEM"
}
