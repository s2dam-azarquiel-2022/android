package net.azarquiel.marvelcompose.view.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.azarquiel.marvelcompose.R
import net.azarquiel.marvelcompose.model.Hero
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.marvelcompose.navigation.Destinations
import net.azarquiel.marvelcompose.view.ui.Previews
import net.azarquiel.marvelcompose.view.ui.debugPlaceholder
import net.azarquiel.marvelcompose.view.ui.theme.AppTheme
import net.azarquiel.marvelcompose.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.HeroList.route,
                ) {
                    composable(route = Destinations.HeroList.route) {
                        Scaffold(
                            topBar = {
                                SmallTopAppBar(
                                    title = { Text(text = "Marvel") },
                                    colors = TopAppBarDefaults.smallTopAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    actions = {
                                        val login by viewModel.login.observeAsState()
                                        IconButton(onClick = {
                                            if (login == null) navController.navigate(Destinations.Login.route)
                                            else viewModel.logout()
                                        }) {
                                            val id =
                                                if (login == null) R.drawable.ic_account
                                                else R.drawable.ic_logout
                                            Icon(painter = painterResource(id = id), contentDescription = null)
                                        }
                                    }
                                )
                            },
                        ) {
                            Box(modifier = Modifier.padding(it)) {
                                HeroList(viewModel) {
                                    navController.navigate(Destinations.HeroDetails.route)
                                    viewModel.setSelectedHero(it)
                                }
                            }
                        }
                    }
                    composable(route = Destinations.HeroDetails.route) {
                        HeroDetails(viewModel)
                    }
                    composable(route = Destinations.Login.route) {
                        var nick by rememberSaveable { mutableStateOf("") }
                        var pass by rememberSaveable { mutableStateOf("") }
                        val login by viewModel.login.observeAsState()
                        if (login == null) LoginFields(nick, { nick = it }, pass, { pass = it }) {
                            viewModel.login(nick = nick, pass = pass)
                        } else navController.popBackStack(
                            Destinations.HeroList.route,
                            inclusive = false,
                            saveState = false
                        )
                    }
                }

            }
        }
    }

    @Composable
    private fun LoginFields(
        nick: String,
        onNickChange: (String) -> Unit,
        pass: String,
        onPassChange: (String) -> Unit,
        onSubmit: () -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = nick,
                onValueChange = onNickChange,
                label = { Text(text = "Nick") },
            )
            OutlinedTextField(
                value = pass,
                onValueChange = onPassChange,
                label = { Text(text = "Pass") },
            )
            Button(onClick = {
                if (!(nick.isEmpty() || pass.isEmpty())) onSubmit()
                else Toast.makeText(this@MainActivity, "Fill the fields", Toast.LENGTH_SHORT).show()
            }) { Text(text = "Login | Register") }
        }
    }

    @Composable
    private fun HeroList(viewModel: MainViewModel, click: (Hero) -> Unit) {
        val heroes by viewModel.heroes.observeAsState(emptyList())
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(heroes) { HeroCard(hero = it, click = click) }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    private fun HeroCard(
        hero: Hero = Previews.hero,
        click: (Hero) -> Unit = { _ -> }
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

    @Composable
    private fun HeroImage(hero: Hero, modifier: Modifier) {
        AsyncImage(
            model = "${hero.img.url}/standard_fantastic.${hero.img.extension}",
            placeholder = debugPlaceholder(debugPreview = R.drawable.a_bomb),
            contentDescription = null,
            modifier = modifier,
        )
    }

    @Composable
    private fun HeroDetails(viewModel: MainViewModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val hero by viewModel.selectedHero.observeAsState()
            hero?.let { hero ->
                HeroImage(hero = hero, modifier = Modifier)
                Text(text = hero.name, fontSize = 20.sp)
                val rate by viewModel.selectedHeroRate.observeAsState(0)
                LazyRow { items(5) { i -> RateStar(i < rate) } }
                Text(text = hero.description, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1f))
                // Row { (1..5).forEach {  } }
            }
        }
    }

    @Preview
    @Composable
    private fun RateStar(isOn: Boolean = false) {
        val id =
            if (isOn) android.R.drawable.star_big_on
            else android.R.drawable.star_big_off
        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}
