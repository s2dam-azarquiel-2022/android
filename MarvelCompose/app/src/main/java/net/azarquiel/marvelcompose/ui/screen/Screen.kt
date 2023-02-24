package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.azarquiel.marvelcompose.ui.navigation.Destination

//fun NavGraphBuilder.screen(
//    destination: Destination,
//    content: @Composable () -> Unit,
//) = composable(route = destination.route) { content() }

@OptIn(ExperimentalMaterial3Api::class)
inline fun <T : ViewModel> NavGraphBuilder.screen(
    destination: Destination,
    crossinline topAppBar: @Composable (T) -> Unit,
    crossinline content: @Composable (T, PaddingValues) -> Unit,
    crossinline viewModelFn: @Composable () -> T,
) = composable(
    route = destination.route,
) {
    val viewModel = viewModelFn()
    Scaffold(
        topBar = { topAppBar(viewModel) },
        content = { padding -> content(viewModel, padding) }
    )
}

// Meant for previews
@Suppress("NOTHING_TO_INLINE")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
inline fun Screen(
    noinline topAppBar: @Composable () -> Unit,
    noinline content: @Composable (PaddingValues) -> Unit,
) = Scaffold(
    topBar = topAppBar,
    content = content,
)
