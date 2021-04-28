
import commands.Homework
import commands.CourseDefinition
import commands.GroupDefinition
import commands.Timetable
import commands.TimetableToday
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import subjects.Course
import subjects.Specialization
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class OrganizerBot : TelegramLongPollingCommandBot() {
    override fun getBotToken(): String {
        return System.getenv("1728118655:AAEQOKogNSnI0WgkTNzpbpufH6LXi6HP6lQ")
    }

    override fun getBotUsername(): String {
        return System.getenv("JB_MSE_bot")
    }

    init {
        register(TimetableToday())
        register(Timetable())
        register(Homework())
        register(CourseDefinition())
        register(GroupDefinition())
        register(Specialization())
    }

    override fun processNonCommandUpdate(update: Update?) {
        if (update!!.hasMessage() && update.message.hasText()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true
            val currCommand = update.message.text
            System.err.println(currCommand)
            val api = HTTPAPI()
            val day = DayOfWeek.values().find { d ->
                d.getDisplayName(TextStyle.FULL, Locale.US) == update.message.text
            }
            if (day != null) {
                val lessons = api.scheduleForTheDay(day, message.chatId)
                message.text = lessons.lessons.joinToString("\n")
            } else {
                when (currCommand) {
                    "Матлогика/Жаворонков" -> {
                        message.text = api.scheduleOFCourse(Course("Matlogic", 1))
                    }
                    "Матлогика/Халанский" -> {
                        message.text = api.scheduleOFCourse(Course("Matlogic", 2))
                    }
                    "Алгоритмы/Мишунин" -> {
                        message.text = api.scheduleOFCourse(Course("Algoritms", 1))
                    }
                    "Алгоритмы/Лапенок" -> {
                        message.text = api.scheduleOFCourse(Course("Algoritms", 2))
                    }
                    "Формальные языки/Халанский" -> {
                        message.text = api.scheduleOFCourse(Course("FormaLAng", 1))
                    }
                    "Формальные языки/Вербицкая" -> {
                        message.text = api.scheduleOFCourse(Course("FormalLAng", 2))
                    }
                    "Предмет по специализации" -> {
                        message.text = "specialization"
                    }
                    "С++" -> message.text = "C++"
                    "Матстат" -> message.text = "Matstat"
                    "Типы в ЯП" -> message.text = "Type"
                    else -> message.text = "invalid command"
                }
            }
            execute(message)
        }
    }
}