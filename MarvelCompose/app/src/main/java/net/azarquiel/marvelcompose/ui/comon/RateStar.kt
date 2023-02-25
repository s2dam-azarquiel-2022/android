package net.azarquiel.marvelcompose.ui.comon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import net.azarquiel.marvelcompose.R

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun ClickableRateStar(
    noinline click: () -> Unit,
    isOn: Boolean
) = IconButton(
    modifier = Modifier.background(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp))
    ),
    onClick = click,
    content = { RateStar(isOn) }
)

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun RateStar(
    isOn: Boolean,
) = Image(
    painter = painterResource(id = starId(isOn)),
    contentDescription = null,
    modifier = Modifier.size(30.dp),
    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
)

@Suppress("NOTHING_TO_INLINE")
inline fun starId(isOn: Boolean) =
    if (isOn) R.drawable.star_filled
    else R.drawable.star_empty

@Composable
@Preview(showBackground = true)
private fun RateStarFilledPreview() = RateStar(true)

@Composable
@Preview(showBackground = true)
private fun RateStarEmptyPreview() = RateStar(false)

@Composable
@Preview(showBackground = true)
private fun ClickableRateStarPreview() {
    val isOnFlow = MutableStateFlow(true)
    val isOn by isOnFlow.collectAsState()
    ClickableRateStar(
        click = { isOnFlow.value = !isOnFlow.value },
        isOn = isOn
    )
}
