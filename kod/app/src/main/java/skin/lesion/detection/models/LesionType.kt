package skin.lesion.detection.models

enum class LesionType(val label: String, val description: String) {
    ACK("ACK", "Rogowacenie słoneczne"),
    BCC("BCC", "Rak podstawnokomórkowy"),
    MEL("MEL", "Czerniak"),
    NEV("NEV", "Znamię melanocytowe"),
    SCC("SCC", "Rak kolczystokomórkowy"),
    SEK("SEK", "Łagodne rogowacenie"),

    UNKNOWN("UNKNOWN", "Nieznana zmiana skórna");

    companion object {
        fun mapToLesionType(label: String?): LesionType {
            return entries.firstOrNull { it.label == label } ?: UNKNOWN
        }
    }
}
