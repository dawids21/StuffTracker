package xyz.stasiak.stufftracker.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R

@Composable
fun DepleteDialog(
    productName: String,
    onDepletionConfirmed: () -> Unit,
    onDialogDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        title = {
            Text(
                text = stringResource(
                    R.string.deplete_dialog_title,
                    productName
                )
            )
        },
        text = {
            Text(
                text = stringResource(
                    R.string.deplete_dialog_text,
                    productName
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDepletionConfirmed()
                    onDialogDismissed()
                }
            ) {
                Text(stringResource(R.string.mark_as_empty))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDialogDismissed
            ) {
                Text(stringResource(R.string.not_yet))
            }
        }
    )
}