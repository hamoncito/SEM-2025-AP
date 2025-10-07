package skin.lesion.detection.models

enum class LesionType(val label: String, val description: String) {
    AKIEC("akiec", "Uszkodzenia skóry przez promieniowanie słoneczne"),
    BCC("bcc", "Rak podstawnokomórkowy"),
    BKL("bkl", "Nieszkodliwe rogowacenie"),
    DF("df", "Włókniak twardy"),
    MEL("mel", "Czerniak"),
    NV("nv", "Znamie melanocytowe (pieprzyk)"),
    VASC("vasc", "Nieszkodliwe naczyniowe zmiany skórne"),
    UNKNOWN("", "Nieznana zmiana skórna");

    companion object {
        fun mapToLesionType(label: String?): LesionType? {
            return entries.firstOrNull { it.label == label }
        }
    }
}