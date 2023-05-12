package xyz.stasiak.stufftracker.ui.product.entry

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import xyz.stasiak.stufftracker.ui.product.ProductImage

@Composable
fun ImageInput(imageUri: String?, onValueChange: (Uri) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                onValueChange(it)
            }
        }
    ProductImage(
        imageUri = imageUri,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = { launcher.launch(arrayOf("image/*")) })
    )
}