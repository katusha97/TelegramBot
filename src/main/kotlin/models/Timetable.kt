package models

import Lesson
import kotlinx.serialization.Serializable

@Serializable
data class Timetable(val lessons: List<Lesson>)