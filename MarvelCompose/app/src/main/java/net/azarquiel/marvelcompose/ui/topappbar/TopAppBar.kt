package net.azarquiel.marvelcompose.ui.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.topappbar.actions.LoginAction
import net.azarquiel.marvelcompose.viewModel.ILoginCheck

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun TopAppBar(
    title: String,
    noinline actions: @Composable (RowScope.() -> Unit),
) = SmallTopAppBar(
    title = { Text(text = title) },
    colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    actions = actions,
)

@Composable
fun LoginTopAppBar(
    title: String,
    viewModel: ILoginCheck,
) = TopAppBar(title = title) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(false)
    LoginAction(
        isLoggedIn = isLoggedIn,
        login = viewModel::login,
        logout = viewModel::logout,
    )
}

@Composable
@Preview(name = "Login/Logout simulation", showBackground = true)
private fun LoginTopAppBarPreview() = LoginTopAppBar("LoginPreview", Previews.LoginCheck)
