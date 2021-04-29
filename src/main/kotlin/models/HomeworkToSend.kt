package models

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkToSend(val fileId: String, val nameOfSubject: String)
