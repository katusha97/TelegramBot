import commands.Timetable
import commands.TimetableToday
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class OrganizerBot : TelegramLongPollingCommandBot() {
    override fun getBotToken(): String {
        return "1728118655:AAEQOKogNSnI0WgkTNzpbpufH6LXi6HP6lQ"
    }

    override fun getBotUsername(): String {
        return "JB_MSE_bot"
    }

    init {
        register(TimetableToday())
        register(Timetable())
    }

    override fun processNonCommandUpdate(update: Update?) {
        if (update!!.hasMessage() && update.message.hasText()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true
            val day = DayOfWeek.values().find { d ->
                d.getDisplayName(TextStyle.FULL, Locale.US) == update.message.text
            }
            if (day != null) {
                val api = HTTPAPI()
                val lessons = api.scheduleForTheDay(day)
                message.text = lessons.joinToString("\n")
            } else {
                message.text = "invalid command"
            }
            execute(message)
        }
    }
}