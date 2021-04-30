import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.*
import java.time.DayOfWeek
import java.time.LocalDate

class HTTPAPI : API {

    private val client = HttpClient(CIO) { install(JsonFeature) }

    override fun scheduleForTheDay(userId: String, day: DayOfWeek): Timetable {
        var json = ""
        runBlocking {
            json = client.get<String>(System.getenv("JB_BOT_HOST") + "day") {
                header("Content-Type", "application/json")
                header("Ident", userId)
                body = ScheduleParams(day.value)
            }
        }
        System.err.println(json);
        return Json.decodeFromString(json)
    }

    override fun scheduleForToday(userId: String): Timetable {
        return scheduleForTheDay(userId, LocalDate.now().dayOfWeek)
    }

    override fun scheduleOFCourse(userId: String, course: Course): String {
        var res = ""
        runBlocking {
            res = client.get(System.getenv("JB_BOT_HOST") + "set_lessons") {
                header("Content-Type", "application/json")
                header("Ident", userId)
                body = course
            }
        }
        return res
    }

    override fun scheduleOFSpecialCourse(userId: String, course: SpecialCourse): String {
        var res = ""
        runBlocking {
            res = client.get(System.getenv("JB_BOT_HOST") + "set_lessons") {
                header("Content-Type", "application/json")
                header("Ident", userId)
                body = course
            }
        }
        return res
    }

    override fun sendHW(userId: String, homework: HomeworkToSend): String {
        var res = ""
        runBlocking {
            res = client.get(System.getenv("JB_BOT_HOST") + "homework/send") {
                header("Content-Type", "application/json")
                header("Ident", userId)
                body = homework
            }
        }
        return res
    }

    override suspend fun getAllHW(userId: String): List<HomeworkResponse> {
        val response = client.get<String>(System.getenv("JB_BOT_HOST") + "homework") {
            header("Ident", userId)
        }
        return Json.decodeFromString(response)
    }

    override suspend fun getHW(userId: String, subject: Subject): HomeworkResponse? {
        val response = client.get<String>(System.getenv("JB_BOT_HOST") + "homework/subj") {
            header("Content-Type", "application/json")
            header("Ident", userId)
            body = SubjectRequest(subject.subjectName)
        }
        if (response == "null") {
            return null;
        }
        return Json.decodeFromString(response)
    }

    override suspend fun getPerfreport(userId: String, subject: Subject): PerfreportResponse {
        val response = client.get<String>(System.getenv("JB_BOT_HOST") + "perfreport") {
            header("Content-Type", "application/json")
            header("Ident", userId)
            body = SubjectRequest(subject.subjectName)
        }
        return Json.decodeFromString(response)
    }
}