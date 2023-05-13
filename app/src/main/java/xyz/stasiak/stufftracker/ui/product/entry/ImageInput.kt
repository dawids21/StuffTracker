package xyz.stasiak.stufftracker.ui.product.entry

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.product.ProductImage
import java.io.File

@Composable
fun ImageInput(imageUri: String?, onValueChange: (Uri) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val permission = Manifest.permission.CAMERA
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                onValueChange(it)
            }
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && cameraImageUri != null) {
                onValueChange(cameraImageUri!!)
            }
        }
    )

    fun launchCamera() {
        if (cameraImageUri == null) {
            cameraImageUri = ComposeFileProvider.getImageUri(context)
        }
        cameraLauncher.launch(cameraImageUri!!)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.camera_permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    ProductImage(
        imageUri = imageUri,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = { imagePickerLauncher.launch(arrayOf("image/*")) })
    )
    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                launchCamera()
            } else {
                cameraPermissionLauncher.launch(permission)
            }
        },
    ) {
        Text(
            text = "Take photo"
        )
    }
}

class ComposeFileProvider : FileProvider(R.xml.paths) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.filesDir, "product_images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".file_provider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}