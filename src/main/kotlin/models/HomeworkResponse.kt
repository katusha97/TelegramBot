package models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class HomeworkResponse(
    val date: String,
    val subject: String,
    val text: String,
    val link: String,
) {
    override fun toString(): String {
        val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return """[$subject]($link)
            | $text
            | Дедлайн: ${"${localDate.dayOfMonth}".padStart(2, '0')}\.${
            "${localDate.monthValue}".padStart(
                2,
                '0'
            )
        }""".trimMargin()
    }
}