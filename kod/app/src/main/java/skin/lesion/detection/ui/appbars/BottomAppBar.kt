package skin.lesion.detection.ui.appbars

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import skin.lesion.detection.R

@Composable
fun BottomAppBar(
    showInfo: Boolean,
    onShowInfoChange: (Boolean) -> Unit,
    context: Context,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    BottomAppBar(
        windowInsets = WindowInsets(0),
        modifier = Modifier.height(70.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    if (showInfo)
                        onShowInfoChange(false)
                    else {
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
                }) {
                    Icon(
                        painter = painterResource(R.drawable.add_icon),
                        contentDescription = "Dodaj zdjęcie",
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(onClick = {
                    onShowInfoChange(true)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.info_icon),
                        contentDescription = "Info",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    )
}