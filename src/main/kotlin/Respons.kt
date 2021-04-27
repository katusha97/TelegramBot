import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.DefaultRequest.Feature.install
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import javax.ws.rs.client.Client

// 1. Расписание на день
// 2. Расписание на неделю
// 3. Отправить дз
// 4. Получить дз все
// 5. Получить близжайшие дедлайны
// 6. Получить дз по определенному предмету

@Serializable
data class Lesson(
    val start_time: Int,
    val end_time: Int,
    val name: String,
    val subtype: String,
    val link: String
)

@Serializable
data class ScheduleParams(val day: Int)

interface API {
    fun scheduleForToday(): List<Lesson>
    fun scheduleForTheDay(day: String): List<Lesson>
    fun scheduleForWeek()
    fun sendHW()
    fun getAllHW()
    fun getHWForTheDay(day: String)
}

class APIImpl : API {

    private val client = HttpClient(CIO)

    private fun getCurrDay(day: String): Int {
        return when (day) {
            "понедельник" -> 1
            "вторник" -> 2
            "среда" -> 3
            "четверг" -> 4
            "пятница" -> 5
            "суббота" -> 6
            "воскресенье" -> 7
            else -> -1
        }
    }

    override fun scheduleForTheDay(day: String): List<Lesson> {
        val currDay = getCurrDay(day)
        var json = ""
        runBlocking {
            json = client.get<String>("http://localhost:5000") {
                body = ScheduleParams(currDay)
            }
        }
        if (currDay != -1) {
//            val json = """
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
        return emptyList()
    }


    override fun scheduleForToday(): List<Lesson> {
        return when (LocalDate.now().dayOfWeek.name) {
            "MONDAY" -> scheduleForTheDay("понедельник")
            "TUESDAY" -> scheduleForTheDay("вторник")
            "WEDNESDAY" -> scheduleForTheDay("среда")
            "THURSDAY" -> scheduleForTheDay("четверг")
            "FRIDAY" -> scheduleForTheDay("пятница")
            "SATURDAY" -> scheduleForTheDay("суббота")
            "SUNDAY" -> scheduleForTheDay("воскресенье")
            else -> scheduleForTheDay("ошибка")
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

    override fun getHWForTheDay(day: String) {
        TODO("Not yet implemented")
    }
}