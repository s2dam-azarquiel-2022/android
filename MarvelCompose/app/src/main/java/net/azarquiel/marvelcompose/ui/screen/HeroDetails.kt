package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import net.azarquiel.marvelcompose.di.ViewModels
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.ui.Destination
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.bottomAppBar.RateBottomAppBar
import net.azarquiel.marvelcompose.ui.comon.HeroImage
import net.azarquiel.marvelcompose.ui.comon.RateStar
import net.azarquiel.marvelcompose.ui.topAppBar.LoginTopAppBar
import net.azarquiel.marvelcompose.viewModel.IHeroDetailsViewModel

@Suppress("FunctionName")
fun NavGraphBuilder.HeroDetailsScreen(
    navController: NavHostController,
) = screen(
    destination = Destination.HeroDetails,
    topAppBar = { viewModel, _ -> HeroDetailsScreenTopAppBar(viewModel = viewModel) },
    bottomAppBar = { viewModel, _ -> HeroDetailsScreenBottomAppBar(viewModel = viewModel) },
    content = { viewModel, _, padding -> HeroDetails(Modifier.padding(padding), viewModel) },
    viewModelFn = {
        ViewModels.heroDetailsViewModel(
            heroId = it.arguments?.getString("heroId") ?: "-1",
            loginFn = { navController.navigate(Destination.Login.route) },
        )
    }
)

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroDetailsScreenTopAppBar(
    viewModel: IHeroDetailsViewModel,
) {
    val hero by viewModel.hero.collectAsState(null)
    LoginTopAppBar(title = hero?.name ?: "", viewModel = viewModel)
}

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroDetailsScreenBottomAppBar(
    viewModel: IHeroDetailsViewModel,
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(false)
    if (isLoggedIn) RateBottomAppBar(viewModel = viewModel)
}

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroDetails(
    modifier: Modifier,
    viewModel: IHeroDetailsViewModel,
) {
    val hero by viewModel.hero.collectAsState(null)
    hero?.let {
        HeroDetails(
            modifier = modifier,
            hero = it,
            avgRateFlow = viewModel.avgRate
        )
    }
}

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroDetails(
    modifier: Modifier,
    hero: Hero,
    avgRateFlow: StateFlow<Int>,
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(5.dp),
    verticalArrangement = Arrangement.spacedBy(5.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    HeroImage(hero = hero, modifier = Modifier)
    val avgRate by avgRateFlow.collectAsState()
    LazyRow { items(5) { i -> RateStar(i < avgRate) } }
    Text(text = hero.name, fontSize = 20.sp)
    Text(text = hero.description, textAlign = TextAlign.Center)
}

@Composable
@Preview(showBackground = true)
private fun HeroDetailsPreview(
    modifier: Modifier = Modifier,
) = HeroDetails(
    modifier = modifier,
    hero = Previews.Hero,
    avgRateFlow = Previews.HeroDetailsViewModel.avgRate,
)

@Composable
@Preview(showBackground = true)
private fun HeroDetailsScreenBottomAppBarPreview() =
    HeroDetailsScreenBottomAppBar(viewModel = Previews.HeroDetailsViewModel)

@Composable
@Preview(showBackground = true)
private fun HeroDetailsScreenTopAppBarPreview() =
    HeroDetailsScreenTopAppBar(viewModel = Previews.HeroDetailsViewModel)

@Composable
@Preview(showBackground = true)
private fun HeroDetailsScreenPreview() = Screen(
    topAppBar = { HeroDetailsScreenTopAppBarPreview() },
    bottomAppBar = { HeroDetailsScreenBottomAppBarPreview() },
    content = { HeroDetailsPreview(Modifier.padding(it)) },
)