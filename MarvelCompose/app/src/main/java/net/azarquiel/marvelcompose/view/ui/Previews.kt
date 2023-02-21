package net.azarquiel.marvelcompose.view.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.model.MarvelImg

object Previews {
    val hero = Hero(
        id = 1017100,
        name = "A-Bomb (HAS)",
        description = "Rick Jones has been Hulk's best bud since day one, but now he's more than a friend...he's a teammate! Transformed by a Gamma energy explosion, A-Bomb's thick, armored skin is just as strong and powerful as it is blue. And when he curls into action, he uses it like a giant bowling ball of destruction! ",
        img = MarvelImg(
            url = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
            extension = "jpg",
        )
    )
}

class HeroProvider(
    override val values: Sequence<Hero> = sequenceOf(Previews.hero)
) : PreviewParameterProvider<Hero>
