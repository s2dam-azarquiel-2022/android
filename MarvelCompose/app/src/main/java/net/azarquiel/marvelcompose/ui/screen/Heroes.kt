package net.azarquiel.marvelcompose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.azarquiel.marvelcompose.di.ViewModels
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.ui.Destination
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.comon.HeroImage
import net.azarquiel.marvelcompose.ui.topAppBar.LoginTopAppBar
import net.azarquiel.marvelcompose.viewModel.IHeroesViewModel

@Suppress("FunctionName")
fun NavGraphBuilder.HeroesScreen(
    navController: NavHostController,
) = screen(
    destination = Destination.HeroList,
    topAppBar = { viewModel, _ -> HeroesScreenTopAppBar(viewModel = viewModel) },
    content = { viewModel, _, padding -> HeroList(Modifier.padding(padding), viewModel) },
    viewModelFn = { ViewModels.heroesViewModel(
        loginFn = { navController.navigate(Destination.Login.route) },
        onHeroClickFn = { navController.navigate(Destination.HeroDetails.createRoute(it)) },
    ) }
)

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroesScreenTopAppBar(
    viewModel: IHeroesViewModel,
) = LoginTopAppBar(title = "Marvel", viewModel = viewModel)

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun HeroList(
    modifier: Modifier,
    viewModel: IHeroesViewModel,
) {
    val heroes by viewModel.heroes.observeAsState(emptyList())
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) { items(heroes) { HeroCard(hero = it, click = viewModel::onHeroClick) } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeroCard(
    hero: Hero,
    click: (Hero) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        onClick = { click(hero) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp),
        ) {
            HeroImage(hero = hero, modifier = Modifier)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = hero.name)
        }
    }
}

// Previews

@Composable
@Preview(showBackground = true)
private fun HeroCardPreview() = HeroCard(
    hero = Previews.Hero,
    click = {}
)

@Composable
@Preview(showBackground = true)
private fun HeroListPreview(
    modifier: Modifier = Modifier,
) = HeroList(
    modifier = modifier,
    viewModel = Previews.HeroesViewModel,
)

@Composable
@Preview(showBackground = true)
private fun HeroesScreenTopAppBarPreview() =
    HeroesScreenTopAppBar(viewModel = Previews.HeroesViewModel)

@Composable
@Preview(showBackground = true)
private fun HeroesScreenPreview() = Screen(
    topAppBar = { HeroesScreenTopAppBarPreview() },
    content = { HeroListPreview(Modifier.padding(it)) }
)
