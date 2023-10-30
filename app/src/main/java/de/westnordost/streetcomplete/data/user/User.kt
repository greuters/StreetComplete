package de.westnordost.streetcompletegpx.data.user

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Long, val displayName: String)
