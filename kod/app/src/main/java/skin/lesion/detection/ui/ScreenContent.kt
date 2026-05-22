package skin.lesion.detection.ui

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skin.lesion.detection.models.PredictionResult
import skin.lesion.detection.ui.screens.InfoScreen
import skin.lesion.detection.ui.screens.PredictionScreen

@Composable
fun ScreenContent(
    modifier: Modifier,
    showInfo: Boolean,
    imageBitmap: Bitmap?,
    predictions: List<PredictionResult>?
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showInfo) {
            InfoScreen()
        } else {
            PredictionScreen(
                imageBitmap = imageBitmap,
                predictions = predictions
            )
        }
    }
}

