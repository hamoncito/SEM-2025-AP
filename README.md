# 📱 Skin Lesion Classifier – Aplikacja Android wykorzystująca model machine learning do klasyfikacji zmian skórnych

Aplikacja mobilna umożliwiająca klasyfikację zmian skórnych na podstawie zdjęcia użytkownika, wykorzystując wytrenowany model uczenia maszynowego oparty na zbiorze danych [HAM10000](https://dataverse.harvard.edu/dataset.xhtml?persistentId=doi:10.7910/DVN/DBW86T).

## 🎯 Funkcjonalność aplikacji
MVP – wersja demonstracyjna z podstawową funkcjonalnością
- Możliwość wykonania zdjęcia zmiany skórnej lub wybrania zdjęcia z galerii
- Przesłanie zdjęcia do lokalnego modelu ML
- Predykcja klasy zmiany skórnej (jedna z 7 klas)
- Wyświetlenie wyniku klasyfikacji

## 🛠 Technologie
- Kotlin
- Jetpack Compose
- TensorFlow Lite
- Python

## 📚 Wykorzystane biblioteki - Python

| Biblioteka | Zastosowanie |
|------------|--------------|
| `PIL` | Wczytywanie i przetwarzanie obrazów |
| `os` | Operacje na plikach i ścieżkach |
| `matplotlib.pyplot` | Wizualizacja danych i wyników |
| `tensorflow` | Budowa, trenowanie i eksport modelu ML |
| `pandas` | Obsługa danych tabelarycznych |
| `numpy` | Operacje numeryczne |
| `sklearn.model_selection` | Podział danych |
| `sklearn.utils.class_weight` | Wagi klas |
| `sklearn.metrics` | Raport klasyfikacji, macierz błędów |
| `tensorflow.keras.preprocessing.image` | Augmentacja obrazów |
| `tensorflow.keras.applications.EfficientNetB0` | Architektura CNN |
| `tensorflow.keras.layers` | Warstwy sieci |
| `tensorflow.keras.models` | Budowa modelu |
| `tensorflow.keras.metrics` | Metryki AUC, Precision, Recall |
| `tensorflow.keras.callbacks` | EarlyStopping, ReduceLROnPlateau |
| `random` | Losowe operacje |

## 📚 Wykorzystane biblioteki – Kotlin / Android

| Biblioteka | Zastosowanie |
|------------|--------------|
| `tensorflow.lite` | Obsługa modelu ML w aplikacji |
| `tensorflow.lite.task.vision` | Przetwarzanie obrazu i predykcja |
| `tensorflow.lite.support` | Wsparcie dla danych wejściowych/wyjściowych |
| `androidx.core.ktx` | Rozszerzenia Kotlin dla Androida |
| `androidx.lifecycle.runtime.ktx` | Obsługa cyklu życia komponentów |
| `androidx.activity.compose` | Integracja Compose z aktywnościami |
| `androidx.compose.*` | Interfejs użytkownika w Jetpack Compose |

## 🧬 Klasy zmian skórnych

| Skrót | Pełna nazwa | Opis | Przykładowe zdjęcie |
|-------|-------------|------|----------------------|
| akiec | Actinic keratoses | Zmiany przedrakowe, uszkodzenia skóry przez promieniowanie słoneczne | ![akiec](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/akiec.png) |
| bcc   | Basal cell carcinoma | Rak podstawnokomórkowy | ![bcc](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/bcc.png) |
| bkl   | Benign keratosis-like lesions | Łagodne zmiany keratotyczne, nieszkodliwe rogowacenie, może się pojawić w wyniku starzenia skóry | ![bkl](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/bkl.png) |
| df    | Dermatofibroma | Włókniak twardy, niegroźny guz | ![df](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/df.png) |
| nv    | Melanocytic nevi | Znamię melanocytowe – „pieprzyk”, nieszkodliwy guz | ![nv](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/nv.png) |
| mel   | Melanoma | Czerniak, złośliwy nowotwór | ![mel](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/mel.png) |
| vasc  | Vascular lesions | Nieszkodliwe zmiany naczyniowe | ![vasc](https://raw.githubusercontent.com/hamoncito/SEM-2025-AP/main/przykladowe-obrazy/vasc.png) |

## ⚖️ Licencja i prawa autorskie

Ten projekt jest objęty licencją **Creative Commons Attribution 4.0 International (CC BY 4.0)**.
