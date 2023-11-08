package de.westnordost.streetcompletegpx.quests.shop_type

sealed interface ShopTypeAnswer

object IsShopVacant : ShopTypeAnswer
data class ShopType(val tags: Map<String, String>) : ShopTypeAnswer
