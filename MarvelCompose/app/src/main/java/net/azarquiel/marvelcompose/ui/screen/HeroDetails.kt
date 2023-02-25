package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.azarquiel.marvelcompose.di.ViewModels
import net.azarquiel.marvelcompose.ui.Destination
import net.azarquiel.marvelcompose.ui.comon.HeroImage
import net.azarquiel.marvelcompose.ui.topappbar.LoginTopAppBar
import net.azarquiel.marvelcompose.viewModel.IHeroDetailsViewModel

@Suppress("FunctionName")
fun NavGraphBuilder.HeroDetailsScreen(
    navController: NavHostController,
) = screen(
    destination = Destination.HeroDetails,
    topAppBar = { viewModel, _ -> HeroDetailsScreenTopAppBar(viewModel = viewModel) },
    content = { viewModel, _, padding -> HeroDetails(Modifier.padding(padding), viewModel) },
    viewModelFn = { ViewModels.heroDetailsViewModel(
        heroId = it.arguments?.getString("heroId") ?: "-1",
        loginFn = { navController.navigate(Destination.Login.route) },
    ) }
)

@Composable
fun HeroDetailsScreenTopAppBar(
    viewModel: IHeroDetailsViewModel,
) {
    val hero by viewModel.hero.collectAsState(null)
    LoginTopAppBar(title = hero?.name ?: "", viewModel = viewModel)
}

@Composable
fun HeroDetails(
    modifier: Modifier,
    viewModel: IHeroDetailsViewModel,
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(5.dp),
    verticalArrangement = Arrangement.spacedBy(5.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    val hero by viewModel.hero.collectAsState(null)
    hero?.let {
        HeroImage(hero = it, modifier = Modifier)
        Text(text = it.name, fontSize = 20.sp)
        Text(text = it.description, textAlign = TextAlign.Center)
    }
}
