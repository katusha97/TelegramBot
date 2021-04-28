import kotlinx.serialization.Serializable

@Serializable
data class Timetable(val lessons: List<Lesson>)

@Serializable
data class Lesson(
    val start_time: Int,
    val end_time: Int,
    val name: String,
    val subtype: String,
    val link: String
) {
    override fun toString(): String {
        return "${formatTime(start_time)} \\- ${formatTime(end_time)} [$name]($link) \\($subtype\\)"
    }

    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = seconds / 60 - hours * 60
        val hoursStr = "$hours".padStart(2, '0')
        val minutesStr = "$minutes".padStart(2, '0')
        return "$hoursStr:$minutesStr"
    }
}
