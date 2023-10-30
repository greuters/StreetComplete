package de.westnordost.streetcompletegpx.quests.postbox_ref

sealed interface PostboxRefAnswer

data class PostboxRef(val ref: String) : PostboxRefAnswer
object NoVisiblePostboxRef : PostboxRefAnswer
