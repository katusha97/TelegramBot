import models.*
import java.time.DayOfWeek

// 1. Расписание на день
// 2. Расписание на неделю
// 3. Отправить дз
// 4. Получить дз все
// 5. Получить ближайшие дедлайны
// 6. Получить дз по определенному предмету

interface API {
    fun scheduleForToday(userId: String): Timetable
    fun scheduleForTheDay(userId: String, day: DayOfWeek): Timetable
    fun scheduleOFCourse(userId: String, course: Course): String
    fun scheduleOFSpecialCourse(userId: String, course: SpecialCourse): String
    fun sendHW(userId: String, homework: HomeworkToSend): String
    suspend fun getAllHW(userId: String): List<HomeworkResponse>
    suspend fun getHW(userId: String, subject: Subject): HomeworkResponse
    suspend fun getPerfreport(userId: String, subject: Subject): PerfreportResponse
}