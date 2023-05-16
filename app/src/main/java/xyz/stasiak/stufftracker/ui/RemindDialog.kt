package xyz.stasiak.stufftracker.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R

@Composable
fun RemindDialog(
    productName: String,
    onDialogDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        title = {
            Text(
                text = stringResource(
                    R.string.remind_dialog_title,
                    productName
                )
            )
        },
        text = {
            Text(
                text = stringResource(
                    R.string.remind_dialog_text,
                    productName
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDialogDismissed
            ) {
                Text(stringResource(R.string.add_to_list))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDialogDismissed
            ) {
                Text(stringResource(R.string.close))
            }
        }
    )
}