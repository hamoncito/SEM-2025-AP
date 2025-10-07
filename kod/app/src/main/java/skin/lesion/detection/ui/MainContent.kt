import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import skin.lesion.detection.ui.ScreenLayout
import skin.lesion.detection.TFLiteHelper
import skin.lesion.detection.models.PredictionResult
import java.io.File

@Composable
internal fun MainContent(modifier: Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var predictions by remember { mutableStateOf<List<PredictionResult>?>(null) }

    val tfliteHelper = remember { TFLiteHelper(context) }

    LaunchedEffect(imageBitmap) {
        imageBitmap?.let {
            predictions = tfliteHelper.predict(it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri?.let { uri ->
                val stream = context.contentResolver.openInputStream(uri)
                imageBitmap = android.graphics.BitmapFactory.decodeStream(stream)
                stream?.close()

                imageBitmap?.let {
                    predictions = TFLiteHelper(context).predict(it)
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            predictions = null
            val stream = context.contentResolver.openInputStream(it)
            imageBitmap = android.graphics.BitmapFactory.decodeStream(stream)
            stream?.close()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            predictions = null
            val file = File(context.cacheDir, "skinlesion.jpg")
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            imageUri = uri
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Brak uprawnień do kamery", Toast.LENGTH_SHORT).show()
        }
    }

    ScreenLayout(
        modifier,
        galleryLauncher,
        permissionLauncher,
        imageBitmap,
        predictions,
        context
    )
}

