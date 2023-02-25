package net.azarquiel.marvelcompose.ui.topAppBar.actions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import net.azarquiel.marvelcompose.R

@Composable
fun LoginAction(
    isLoggedIn: Boolean,
    login: () -> Unit,
    logout: () -> Unit,
) {
    if (isLoggedIn) LogoutIcon(click = logout)
    else LoginIcon(click = login)
}

@Composable
private fun LoginIcon(
    click: () -> Unit,
) = IconButton(onClick = click) {
    Icon(
        painter = painterResource(id = R.drawable.ic_account),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun LogoutIcon(
    click: () -> Unit,
) = IconButton(onClick = click) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
    )
}

// Previews

@Composable
@Preview(showBackground = true)
private fun LogoutIconPreview() = LogoutIcon { }

@Composable
@Preview(showBackground = true)
private fun LoginIconPreview() = LoginIcon { }

@Composable
@Preview(name = "Logged out", showBackground = true)
private fun TopAppBarLoginActionPreview() {
    var state by remember { mutableStateOf(false) }
    LoginAction(
        state,
        { state = true },
        { state = false }
    )
}
