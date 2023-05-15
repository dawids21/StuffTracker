package xyz.stasiak.stufftracker.ui.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.AppViewModelProvider
import xyz.stasiak.stufftracker.ui.LoadingIndicator
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(viewModel.loginState) {
        if (viewModel.loginState) {
            navigateToHome()
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.onSignedIn(result.data ?: return@rememberLauncherForActivityResult)
            }
        }
    )
    if (viewModel.loginState) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            LoadingIndicator()
        }
        return
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                viewModel.signIn(launcher)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        ) {
            Text(text = stringResource(R.string.login_with_google))
        }
    }
}

@Composable
private fun Logo(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name),
        fontFamily = FontFamily(Font(R.font.itim)),
        fontSize = 48.sp,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        LoginScreen(navigateToHome = { })
    }
}