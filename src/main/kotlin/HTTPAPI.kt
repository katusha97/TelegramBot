import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import subjects.Course
import java.time.DayOfWeek
import java.time.LocalDate

class HTTPAPI : API {

    private val client = HttpClient(CIO) { install(JsonFeature) }

    override fun scheduleForTheDay(day: DayOfWeek, userId: String): Timetable {
        var json = ""
        runBlocking {
            json = client.get<String>("http://94.103.83.6:5001/day") {
                header("Content-Type", "application/json")
                header("Ident", userId)
                body = ScheduleParams(day.value)
            }
        }
        return Json.decodeFromString(json)
    }

    override fun scheduleForToday(userId: String): Timetable {
        return scheduleForTheDay(LocalDate.now().dayOfWeek, userId)
    }

    override fun scheduleOFCourse(course: Course): String {
        var json = ""
        runBlocking {
            json = client.get("http://94.103.83.6:5001/set_lessons") {
                header("Content-Type", "application/json")
                body = course
            }
        }
        return json
    }

    override fun scheduleForWeek() {
        TODO("Not yet implemented")
    }

    override fun sendHW() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllHW(): List<Homework> {
        val response = client.get<String>("http://94.103.83.6:5001/homework")
        return Json.decodeFromString(response)
    }

    override fun getHWForTheDay(day: DayOfWeek) {
        TODO("Not yet implemented")
    }
}