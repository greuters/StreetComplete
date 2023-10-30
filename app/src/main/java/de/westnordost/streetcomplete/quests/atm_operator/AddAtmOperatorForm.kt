package de.westnordost.streetcompletegpx.quests.atm_operator

import de.westnordost.streetcompletegpx.quests.ANameWithSuggestionsForm

class AddAtmOperatorForm : ANameWithSuggestionsForm<String>() {

    override val suggestions: List<String>? get() = countryInfo.atmOperators

    override fun onClickOk() {
        applyAnswer(name!!)
    }
}
