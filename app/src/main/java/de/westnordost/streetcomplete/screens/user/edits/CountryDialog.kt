package de.westnordost.streetcomplete.screens.user.edits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.screens.user.DialogContentWithIconLayout
import de.westnordost.streetcomplete.screens.user.profile.LaurelWreathBadge
import de.westnordost.streetcomplete.screens.user.profile.getLocalRankCurrentWeekProgress
import de.westnordost.streetcomplete.screens.user.profile.getLocalRankProgress
import de.westnordost.streetcomplete.ui.common.OpenInBrowserIcon
import de.westnordost.streetcomplete.ui.theme.headlineSmall
import de.westnordost.streetcomplete.util.ktx.openUri
import java.util.Locale

/** Shows the details for a certain country as a dialog. */
@Composable
fun CountryDialog(
    countryCode: String,
    rank: Int?,
    rankCurrentWeek: Int?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // center everything
        Box(
            modifier = Modifier
                .fillMaxSize()
                // dismiss when clicking wherever - no ripple effect
                .clickable(remember { MutableInteractionSource() }, null) { onDismissRequest() },
            contentAlignment = Alignment.Center
        ) {
            DialogContentWithIconLayout(
                icon = { Flag(countryCode) },
                content = { isLandscape ->
                    CountryInfoDetails(
                        countryCode = countryCode,
                        rank = rank,
                        rankCurrentWeek = rankCurrentWeek,
                        isLandscape = isLandscape
                    )
                },
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun CountryInfoDetails(
    countryCode: String,
    rank: Int?,
    rankCurrentWeek: Int?,
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val countryLocale = Locale("", countryCode)

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = if (isLandscape) Alignment.Start else Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.user_statistics_country_rank2, Locale("", countryCode).displayCountry),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = if (isLandscape) TextAlign.Start else TextAlign.Center
        )

        Row(horizontalArrangement = Arrangement.Center) {
            if (rank != null) {
                LaurelWreathBadge(
                    label = stringResource(R.string.user_profile_all_time_title),
                    value = "#$rank",
                    progress = getLocalRankProgress(rank),
                    modifier = Modifier.weight(1f),
                    animationDelay = 0
                )
            }
            if (rankCurrentWeek != null) {
                LaurelWreathBadge(
                    label = stringResource(R.string.user_profile_current_week_title),
                    value = "#$rankCurrentWeek",
                    progress = getLocalRankCurrentWeekProgress(rankCurrentWeek),
                    modifier = Modifier.weight(1f),
                    animationDelay = 500
                )
            }
        }

        OutlinedButton(
            onClick = {
                val britishCountryName = countryLocale.getDisplayCountry(Locale.UK)
                context.openUri("https://wiki.openstreetmap.org/wiki/$britishCountryName")
            }
        ) {
            OpenInBrowserIcon()
            Text(
                text = stringResource(R.string.user_statistics_country_wiki_link, countryLocale.displayCountry),
                modifier = Modifier.padding(start = 8.dp),
                textAlign = if (isLandscape) TextAlign.Start else TextAlign.Center
            )
        }
    }
}

@Preview(device = Devices.NEXUS_5) // darn small device
@PreviewScreenSizes
@PreviewLightDark
@Composable
private fun PreviewCountryDialog() {
    CountryDialog(
        countryCode = "PH",
        rank = 99,
        rankCurrentWeek = 12,
        onDismissRequest = {}
    )
}
