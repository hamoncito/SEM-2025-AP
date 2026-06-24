package skin.lesion.detection.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import skin.lesion.detection.models.PredictionResult

@Composable
fun PredictionScreen(
    imageBitmap: Bitmap?,
    predictions: List<PredictionResult>?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageBitmap == null) {
            Text(
                text = "Dodaj nową zmianę skórną klikając '+'\n\n" +
                        "1. Upewnij się, że fotografowany fragment skóry jest czysty i widoczny na zdjęciu.\n\n" +
                        "2. Zdjęcie wykonaj w naturalnym świetle, wykorzystując przybliżenie aparatu i zachowując ostrość obrazu.\n\n" +
                        "3. Zmiana skórna powinna być na środku zdjęcia.",
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }

        imageBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Zrobione zdjęcie",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        predictions?.let {
            AnalysisPanel(predictions)
        }
    }
}
