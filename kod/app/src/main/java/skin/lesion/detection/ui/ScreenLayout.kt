package skin.lesion.detection.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import skin.lesion.detection.models.PredictionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenLayout(
    modifier: Modifier,
    galleryLauncher:  ManagedActivityResultLauncher<String, Uri?>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    imageBitmap: Bitmap?,
    predictions: List<PredictionResult>?,
    context: Context,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Klasyfikator zmian skórnych",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
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
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Dodaj zdjęcie")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
}