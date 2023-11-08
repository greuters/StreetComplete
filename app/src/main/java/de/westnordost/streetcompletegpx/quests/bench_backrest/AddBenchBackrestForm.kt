package de.westnordost.streetcompletegpx.quests.bench_backrest

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem
import de.westnordost.streetcompletegpx.quests.bench_backrest.BenchBackrestAnswer.NO
import de.westnordost.streetcompletegpx.quests.bench_backrest.BenchBackrestAnswer.PICNIC_TABLE
import de.westnordost.streetcompletegpx.quests.bench_backrest.BenchBackrestAnswer.YES

class AddBenchBackrestForm : AbstractOsmQuestForm<BenchBackrestAnswer>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { applyAnswer(NO) },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(YES) }
    )

    override val otherAnswers = listOf(
        AnswerItem(R.string.quest_bench_answer_picnic_table) { applyAnswer(PICNIC_TABLE) }
    )
}
