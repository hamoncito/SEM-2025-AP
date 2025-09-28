import android.Manifest
import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import skin.lesion.detection.TFLiteHelper
import java.io.File

@Composable
internal fun MainContent(modifier: Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var prediction by remember { mutableStateOf<String?>(null) }

    val tfliteHelper = remember { TFLiteHelper(context) }

    LaunchedEffect(imageBitmap) {
        imageBitmap?.let {
            prediction = tfliteHelper.predict(it)
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
                    prediction = TFLiteHelper(context).predict(it)
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val stream = context.contentResolver.openInputStream(it)
            imageBitmap = android.graphics.BitmapFactory.decodeStream(stream)
            stream?.close()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Zrób zdjęcie do analizy")

        Button(
            onClick = {
                val options = arrayOf("Aparat", "Galeria")
                AlertDialog.Builder(context)
                    .setTitle("Wybierz źródło")
                    .setItems(options) { _, which ->
                        when (which) {
                            0 -> permissionLauncher.launch(Manifest.permission.CAMERA)
                            1 -> galleryLauncher.launch("image/*")
                        }
                    }
                    .show()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Dodaj zdjęcie")
        }
        imageBitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Zrobione zdjęcie",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        prediction?.let {
            Text("Wykryto: $it", modifier = Modifier.padding(top = 16.dp))
        }
    }
}
