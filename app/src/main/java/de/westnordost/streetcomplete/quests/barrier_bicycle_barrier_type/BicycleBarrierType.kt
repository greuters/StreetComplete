package de.westnordost.streetcompletegpx.quests.barrier_bicycle_barrier_type

sealed interface BicycleBarrierTypeAnswer

enum class BicycleBarrierType(val osmValue: String) : BicycleBarrierTypeAnswer {
    SINGLE("single"),
    DOUBLE("double"),
    TRIPLE("triple"),
    DIAGONAL("diagonal"),
    TILTED("tilted"),
}

object BarrierTypeIsNotBicycleBarrier : BicycleBarrierTypeAnswer
