package models

import kotlinx.serialization.Serializable

@Serializable
data class Notification(val text: String, val users: List<String>)
