import commands.Homework
import commands.CourseDefinition
import commands.GroupDefinition
import commands.Timetable
import commands.TimetableToday
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import subjects.Course
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class OrganizerBot : TelegramLongPollingCommandBot() {
    override fun getBotToken(): String {
        return System.getenv("JB_BOT_TOKEN")
    }

    override fun getBotUsername(): String {
        return System.getenv("JB_BOT_NAME")
    }

    init {
        register(TimetableToday())
        register(Timetable())
        register(Homework())
        register(CourseDefinition())
        register(GroupDefinition())
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
                        message.text = api.scheduleOFCourse(message.chatId, Course("matlogic", 1))
                    }
                    "Матлогика/Халанский" -> {
                        message.text = api.scheduleOFCourse(message.chatId, Course("matlogic", 2))
                    }
                    "Алгоритмы/Мишунин" -> {
                        message.text = api.scheduleOFCourse(message.chatId, Course("algoritms", 1))
                    }
                    "Алгоритмы/Лапенок" -> {
                        message.text = api.scheduleOFCourse(message.chatId, Course("algoritms", 2))
                    }
                    "Формальные языки/Халанский" -> {
                        message.text = api.scheduleOFCourse(message.chatId, Course("formaLAng", 1))
                    }
                    "Формальные языки/Вербицкая" -> {
                        message.text = api.scheduleOFCourse(message.chatId, Course("formalLAng", 2))
                    }
                    "С++" -> message.text = api.scheduleOFCourse(message.chatId, Course("specialization", 1))
                    "Матстат" -> message.text = api.scheduleOFCourse(message.chatId, Course("specialization", 2))
                    "Типы в ЯП" -> message.text = api.scheduleOFCourse(message.chatId, Course("specialization", 3))
                    else -> message.text = "invalid command"
                }
            }
            execute(message)
        }
    }
}