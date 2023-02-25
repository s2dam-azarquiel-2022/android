package net.azarquiel.marvelcompose.ui.bottomAppBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.bottomAppBar.content.Rate
import net.azarquiel.marvelcompose.viewModel.IRateViewModel

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun ZBottomAppBar(
    noinline content: @Composable (RowScope.() -> Unit),
) = BottomAppBar(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    content = content,
)

@Composable
fun RateBottomAppBar(
    viewModel: IRateViewModel,
) = ZBottomAppBar(content = { Rate(viewModel = viewModel) })

@Composable
@Preview(showBackground = true)
private fun RateBottomAppBarPreview() =
    RateBottomAppBar(viewModel = Previews.HeroDetailsViewModel)
