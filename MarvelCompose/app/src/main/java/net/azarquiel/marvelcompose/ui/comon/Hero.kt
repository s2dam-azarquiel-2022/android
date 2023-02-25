package net.azarquiel.marvelcompose.ui.comon

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.azarquiel.marvelcompose.R
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.debugPlaceholder

@Composable
fun HeroImage(hero: Hero, modifier: Modifier) = AsyncImage(
    model = "${hero.img.url}/standard_fantastic.${hero.img.extension}",
    placeholder = debugPlaceholder(debugPreview = R.drawable.a_bomb),
    contentDescription = null,
    modifier = modifier,
)

@Composable
@Preview(showBackground = true)
private fun HeroImagePreview() = HeroImage(
    hero = Previews.Hero,
    modifier = Modifier.padding(10.dp)
)
