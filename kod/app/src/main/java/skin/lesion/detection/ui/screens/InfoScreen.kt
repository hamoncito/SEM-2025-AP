package skin.lesion.detection.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import skin.lesion.detection.R

@Composable
fun InfoScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "O projekcie",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    "Aplikacja jest prototypem systemu klasyfikacji zmian skórnych " +
                            "opartym na konwolucyjnej sieci neuronowej ResNet101, " +
                            "stworzonym w ramach pracy magisterskiej. " +
                            "System służy wyłącznie jako narzędzie edukacyjne, " +
                            "mające na celu poszerzanie świadomości użytkowników " +
                            "na temat potencjalnie niepokojących zmian skórnych.\n\n" +
                            "Model dokonuje inferencji lokalnie, a zdjęcia użytkowników nie są przesyłane do serwera.\n\n" +
                            "Aplikacja nie stanowi diagnozy medycznej. " +
                            "Wszystkie niepokojące zmiany skórne należy niezwłocznie " +
                            "konsultować z dermatologiem.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Typy zmian skórnych wykrywanych przez aplikację",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.nev_sample,
                title = "Znamię melanocytowe (pieprzyk)",
                description =
                    "Łagodna zmiana barwnikowa. " +
                            "Nie stanowi zagrożenia zdrowotnego, usuwa się je w przypadku podejrzenia zezłośliwienia"
            )
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.mel_sample,
                title = "Czerniak",
                description =
                    "Najbardziej agresywny nowotwór skóry, mogący szybko dawać przerzuty. " +
                            "Wczesne wykrycie znacząco zwiększa szanse na skuteczne leczenie." +
                            "Czerniak różni się od zmian melanocytowych nieregularną strukturą, asymetrią oraz niejednorodnością barwy."
            )
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.ack_sample,
                title = "Rogowacenie słoneczne",
                description =
                    "Nieinwazyjna zmiana skórna powstająca w wyniku przewlekłej ekspozycji na promieniowanie UV. " +
                            "Rogowacenie słoneczne wymaga regularnej kontroli dermatologicznej ze względu na ryzyko progresji do inwazyjnego raka płaskonabłonkowego."
            )
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.bcc_sample,
                title = "Rak podstawnokomórkowy",
                description =
                    "Najczęstszy nowotwór skóry, zwykle rosnący powoli i rzadko dający przerzuty. " +
                            "Wymaga leczenia, aby zapobiec miejscowej destrukcji tkanek."
            )
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.scc_sample,
                title = "Rak kolczystokomórkowy",
                description =
                    "Inwazyjny nowotwór skóry wykazujący skłonność tworzenia przerzutów. " +
                            "Wczesna diagnoza i leczenie są kluczowe dla zapobiegania progresji."
            )
        }

        item {
            LesionInfoBlock(
                imageRes = R.drawable.sek_sample,
                title = "Rogowacenie łojotokowe",
                description =
                    "Łagodna, nienowotworowa zmiana skórna o brodawkowatej powierzchni. " +
                            "Często występuje u osób starszych i nie stanowi zagrożenia zdrowotnego."
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}
