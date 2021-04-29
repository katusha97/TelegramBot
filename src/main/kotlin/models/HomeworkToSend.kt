package models

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkToSend(val file_id: String, val subject: String)
