package net.azarquiel.marvelcompose.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import net.azarquiel.marvelcompose.di.ViewModels
import net.azarquiel.marvelcompose.ui.Destination
import net.azarquiel.marvelcompose.ui.Previews
import net.azarquiel.marvelcompose.ui.topappbar.TopAppBar
import net.azarquiel.marvelcompose.viewModel.ILoginViewModel

@Suppress("FunctionName")
fun NavGraphBuilder.LoginScreen(
    navController: NavHostController,
) = screen(
    destination = Destination.Login,
    topAppBar = { _, _ -> LoginScreenTopAppBar() },
    content = { viewModel, _, padding -> LoginFields(Modifier.padding(padding), viewModel) },
    viewModelFn = {
        val context = LocalContext.current
        ViewModels.loginViewModel(
            successfulLoginFn = { navController.popBackStack() },
            unsuccessfulLoginFn = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            },
        )
    }
)

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun LoginScreenTopAppBar(
) = TopAppBar(title = "Login", actions = { })

@Composable
@Suppress("NOTHING_TO_INLINE")
private inline fun LoginFields(
    modifier: Modifier,
    viewModel: ILoginViewModel,
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    val nick by viewModel.nick.collectAsState()
    LoginField(value = nick, onValueChange = viewModel::onNickChange, label = "Nick")
    val pass by viewModel.pass.collectAsState()
    LoginField(value = pass, onValueChange = viewModel::onPassChange, label = "Password")
    Button(onClick = viewModel::onSubmit) { Text(text = "Login | Register") }
}

@Composable
private fun LoginField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
) = OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = label) }
)

@Composable
@Preview(showBackground = true)
private fun LoginFieldPreview() {
    val _text = MutableStateFlow("")
    val text by _text.collectAsState()
    LoginField(value = text, onValueChange = { _text.value = it }, label = "Text")
}

@Composable
@Preview(showBackground = true)
private fun LoginFieldsPreview(
    modifier: Modifier = Modifier,
) =
    LoginFields(modifier = modifier, viewModel = Previews.LoginViewModel)

@Composable
@Preview(showBackground = true)
private fun LoginScreenTopAppBarPreview() =
    LoginScreenTopAppBar()

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() = Screen(
    topAppBar = { LoginScreenTopAppBarPreview() },
    content = { LoginFieldsPreview(Modifier.padding(it)) },
)
