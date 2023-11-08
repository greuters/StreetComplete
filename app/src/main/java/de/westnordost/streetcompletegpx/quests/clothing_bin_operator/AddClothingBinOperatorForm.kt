package de.westnordost.streetcompletegpx.quests.clothing_bin_operator

import de.westnordost.streetcompletegpx.quests.ANameWithSuggestionsForm

class AddClothingBinOperatorForm : ANameWithSuggestionsForm<String>() {

    override val suggestions: List<String>? get() = countryInfo.clothesContainerOperators

    override fun onClickOk() {
        applyAnswer(name!!)
    }
}
