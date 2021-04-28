import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.RuntimeException
import java.time.LocalDate

class HTTPAPI : API {

    private val client = HttpClient(CIO) { install(JsonFeature) }

    override fun scheduleForTheDay(day: Days): List<Lesson> {
        var json = ""
        runBlocking {
            json = client.get<String>("http://localhost:5000/day") {
                header("Content-Type", "application/json")
                body = ScheduleParams(day.number)
            }
        }
//        val json = """
//[
//  {
//    "start_time": 12345,
//    "end_time": 12346,
//    "name": "Матлогика",
//    "subtype": "Семинар",
//    "link": "zoom.us"
//  },
//  {
//    "start_time": 312421,
//    "end_time": 312422,
//    "name": "Алгосы",
//    "subtype": "Лекция",
//    "link": "zoom.us"
//  }
//]
//            """
        return Json.decodeFromString(json)
    }

    override fun scheduleForToday(): List<Lesson> {
        return when (LocalDate.now().dayOfWeek.name) {
            "MONDAY" -> scheduleForTheDay(Days.MONDAY)
            "TUESDAY" -> scheduleForTheDay(Days.TUESDAY)
            "WEDNESDAY" -> scheduleForTheDay(Days.WEDNESDAY)
            "THURSDAY" -> scheduleForTheDay(Days.THURSDAY)
            "FRIDAY" -> scheduleForTheDay(Days.FRIDAY)
            "SATURDAY" -> scheduleForTheDay(Days.SATURDAY)
            "SUNDAY" -> scheduleForTheDay(Days.SUNDAY)
            else -> throw RuntimeException("Invalid day")
        }
    }

    override fun scheduleForWeek() {
        TODO("Not yet implemented")
    }

    override fun sendHW() {
        TODO("Not yet implemented")
    }

    override fun getAllHW() {
        TODO("Not yet implemented")
    }

    override fun getHWForTheDay(day: Days) {
        TODO("Not yet implemented")
    }
}