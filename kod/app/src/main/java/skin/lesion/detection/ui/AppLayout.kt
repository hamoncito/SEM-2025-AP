package skin.lesion.detection.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import skin.lesion.detection.MainViewModel
import skin.lesion.detection.ui.appbars.BottomAppBar
import skin.lesion.detection.ui.appbars.TopAppBar
import java.io.File

@Composable
internal fun AppLayout(
    modifier: Modifier,
    vm: MainViewModel
) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            vm.imageUri?.let { vm.loadImage(it) }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { vm.loadImage(it) }
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
            vm.updateImageUri(uri)
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Brak uprawnień do kamery", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomAppBar(
                showInfo = vm.showInfo,
                onShowInfoChange = { vm.updateShowInfo(it) },
                context = context,
                galleryLauncher = galleryLauncher,
                permissionLauncher = permissionLauncher
            )
        }
    ) { paddingValues ->
        ScreenContent(
            modifier = Modifier.padding(paddingValues),
            imageBitmap = vm.imageBitmap,
            predictions = vm.predictions,
            showInfo = vm.showInfo
        )
    }
}
