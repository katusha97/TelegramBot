import subjects.Course
import java.time.DayOfWeek

// 1. Расписание на день
// 2. Расписание на неделю
// 3. Отправить дз
// 4. Получить дз все
// 5. Получить ближайшие дедлайны
// 6. Получить дз по определенному предмету

interface API {
    fun scheduleForToday(userId: String): Timetable
    fun scheduleForTheDay(day: DayOfWeek, userId: String): Timetable
    fun scheduleOFCourse(course: Course): String
    fun scheduleForWeek()
    fun sendHW()
    fun getAllHW()
    fun getHWForTheDay(day: DayOfWeek)
}