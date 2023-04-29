package xyz.stasiak.stufftracker.ui.settings.shoppinglist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ShoppingListSettingsBody(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var noteName by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = noteName,
            onValueChange = { noteName = it },
            label = { Text(stringResource(R.string.note_name)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onSaveClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListSettingsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ShoppingListSettingsBody(onSaveClick = {})
    }
}