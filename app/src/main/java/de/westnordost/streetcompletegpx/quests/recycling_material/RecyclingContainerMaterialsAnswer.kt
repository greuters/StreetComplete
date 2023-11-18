package de.westnordost.streetcompletegpx.quests.recycling_material

sealed interface RecyclingContainerMaterialsAnswer

object IsWasteContainer : RecyclingContainerMaterialsAnswer
data class RecyclingMaterials(val materials: List<RecyclingMaterial>) : RecyclingContainerMaterialsAnswer
