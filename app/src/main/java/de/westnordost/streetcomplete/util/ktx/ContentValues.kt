package de.westnordost.streetcompletegpx.util.ktx

import android.content.ContentValues

operator fun ContentValues.plus(b: ContentValues) = ContentValues(this).also { it.putAll(b) }
