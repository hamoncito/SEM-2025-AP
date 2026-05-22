package skin.lesion.detection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import skin.lesion.detection.ui.AppLayout

import skin.lesion.detection.ui.theme.SkinlesiondetectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkinlesiondetectionTheme {
                val vm: MainViewModel = viewModel(
                    factory = MainViewModelFactory(application)
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppLayout(
                        modifier = Modifier.padding(innerPadding),
                        vm = vm
                    )
                }
            }
        }
    }
}
