package skin.lesion.detection

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import skin.lesion.detection.models.PredictionResult
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application.applicationContext

    var imageUri by mutableStateOf<Uri?>(null)
        private set

    var imageBitmap by mutableStateOf<Bitmap?>(null)
        private set

    var predictions by mutableStateOf<List<PredictionResult>?>(null)
        private set

    var showInfo by mutableStateOf(false)
        private set

    private val tflite = TFLiteHelper(appContext)

    fun updateShowInfo(value: Boolean) {
        showInfo = value
    }

    fun updateImageUri(uri: Uri) {
        imageUri = uri
    }

    fun loadImage(uri: Uri) {
        imageUri = uri

        val stream = appContext.contentResolver.openInputStream(uri)
        imageBitmap = BitmapFactory.decodeStream(stream)
        stream?.close()

        imageBitmap?.let { bitmap ->
            viewModelScope.launch {
                predictions = tflite.predict(bitmap)
            }
        }
    }
}

