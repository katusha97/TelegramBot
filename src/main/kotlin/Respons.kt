import org.json.JSONArray
import java.net.URL
import java.time.LocalDate
import java.util.*
import org.json.JSONObject
import kotlinx.serialization.*
import kotlinx.serialization.json.*

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

interface API {
    fun scheduleForToday()
    fun scheduleForTheDay(day: String): List<Lesson>
    fun scheduleForWeek()
    fun sendHW()
    fun getAllHW()
    fun getHWForTheDay(day: String)
}

class APIImpl : API {

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
        if (currDay != -1) {
            val json = """
[
  {
    "start_time": 12345,
    "end_time": 12346,
    "name": "Матлогика",
    "subtype": "Семинар",
    "link": "zoom.us"
  },
  {
    "start_time": 312421,
    "end_time": 312422,
    "name": "Алгосы",
    "subtype": "Лекция",
    "link": "zoom.us"
  }
]
            """
            return Json.decodeFromString(json)
        }
        return emptyList()
    }


    override fun scheduleForToday() {
        when (LocalDate.now().dayOfWeek.name) {
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

    }

    override fun sendHW() {
        TODO("Not yet implemented")
    }

    override fun getAllHW() {
        TODO("Not yet implemented")
    }

    override fun getHWForTheDay(day: String) {
        val currDay = getCurrDay(day)
    }
}