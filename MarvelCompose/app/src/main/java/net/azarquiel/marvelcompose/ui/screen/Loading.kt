package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.azarquiel.marvelcompose.ui.UiState
import net.azarquiel.marvelcompose.viewModel.ILoading

@Composable
@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalAnimationApi::class)
inline fun LoadingContent(
    modifier: Modifier,
    viewModel: ILoading,
    loadingText: String,
    crossinline successScreen: @Composable () -> Unit,
    crossinline errorScreen: @Composable () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    AnimatedContent(
        targetState = state,
        transitionSpec = { loadingTransition() }
    ) {
        when (it) {
            UiState.Loading -> LoadingScreen(modifier = modifier, loadingText)
            UiState.Success -> successScreen()
            UiState.Error -> errorScreen()
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
fun loadingTransition() =
    fadeIn(animationSpec = tween(100, delayMillis = 50)) +
            scaleIn(initialScale = 0.90f, animationSpec = tween(200, delayMillis = 50)) with
            fadeOut(animationSpec = tween(100))

@Composable
fun LoadingScreen(
    modifier: Modifier,
    text: String,
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    verticalArrangement = Arrangement.spacedBy(
        space = 5.dp,
        alignment = Alignment.CenterVertically,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = {
        CircularProgressIndicator()
        Text(text = text)
    }
)
