package models

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleParams(val day: Int)