package net.azarquiel.marvelcompose.view.ui.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import net.azarquiel.marvelcompose.R
import net.azarquiel.marvelcompose.model.Hero

@Composable
fun HeroList(liveHeroes: LiveData<List<Hero>>) {
    val heroes by liveHeroes.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(heroes) { HeroCard(hero = it) }
    }
}

@Preview
@Composable
private fun HeroCard(hero: Hero = Previews.hero) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp),
        ) {
            AsyncImage(
                model = "${hero.img.url}/standard_fantastic.${hero.img.extension}",
                placeholder = debugPlaceholder(debugPreview = R.drawable.a_bomb),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = hero.name)
        }

    }
}
