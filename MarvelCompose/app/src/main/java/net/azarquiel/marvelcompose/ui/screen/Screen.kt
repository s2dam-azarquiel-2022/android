package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.azarquiel.marvelcompose.ui.Destination

//fun NavGraphBuilder.screen(
//    destination: Destination,
//    content: @Composable () -> Unit,
//) = composable(route = destination.route) { content() }

inline fun <T : ViewModel> NavGraphBuilder.screen(
    destination: Destination,
    crossinline topAppBar: @Composable (T, NavBackStackEntry) -> Unit,
    crossinline content: @Composable (T, NavBackStackEntry, PaddingValues) -> Unit,
    crossinline viewModelFn: @Composable (NavBackStackEntry) -> T,
) = composable(
    route = destination.route,
    arguments = destination.arguments,
) {
    val viewModel = viewModelFn(it)
    Screen(
        topAppBar = { topAppBar(viewModel, it) },
        content = { padding -> content(viewModel, it, padding) }
    )
}

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
