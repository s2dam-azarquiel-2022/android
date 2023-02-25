package net.azarquiel.marvelcompose.ui.bottomAppBar.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.comon.ClickableRateStar
import net.azarquiel.marvelcompose.viewModel.IRateViewModel

@Composable
fun Rate(
    viewModel: IRateViewModel,
) {
    val rate by viewModel.rate.collectAsState()
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        ),
        content = { starRow(viewModel = viewModel, rate = rate) }
    )
}

@Suppress("NOTHING_TO_INLINE")
private inline fun LazyListScope.starRow(
    viewModel: IRateViewModel,
    rate: Int,
) {
    items(5) { i ->
        ClickableRateStar(
            click = { viewModel.rate(i + 1) },
            isOn = i < rate
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RatePreview() =
    Rate(viewModel = Previews.HeroDetailsViewModel)
