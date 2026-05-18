package skin.lesion.detection.models

enum class LesionType(val label: String, val description: String) {
    MELANOMA("melanoma", "Czerniak"),
    NEVUS("nevus", "Znamie melanocytowe"),
    ACTINIC_KERATOSIS("actinic keratosis", "Uszkodzenia skóry przez UV"),
    BENIGN_KERATOSIS("benign keratosis", "Nieszkodliwe rogowacenie"),
    NMSC("non-melanoma skin cancer", "Rak skóry niebędący czerniakiem"),
    DF("dermatofibroma", "Włókniak twardy"),
    VASC("vascular lesions", "Zmiany naczyniowe"),
    UNKNOWN("unknown", "Nieznana zmiana skórna");

    companion object {
        fun mapToLesionType(label: String?): LesionType {
            return entries.firstOrNull { it.label == label } ?: UNKNOWN
        }
    }
}
