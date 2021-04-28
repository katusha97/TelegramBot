package subjects

import kotlinx.serialization.Serializable

@Serializable
data class Course(val subject: String, val group: Int)

@Serializable
data class SpecialCourse(val subject: String)