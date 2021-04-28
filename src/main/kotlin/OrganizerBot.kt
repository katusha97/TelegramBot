import commands.ScheduleCommand
import commands.ScheduleTodayCommand
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot

class OrganizerBot : TelegramLongPollingCommandBot() {
    override fun getBotToken(): String {
        return "1728118655:AAEQOKogNSnI0WgkTNzpbpufH6LXi6HP6lQ"
    }

    override fun getBotUsername(): String {
        return "JB_MSE_bot"
    }

    init {
        register(ScheduleTodayCommand())
        register(ScheduleCommand())
    }

    override fun processNonCommandUpdate(update: Update?) {
        if (update!!.hasMessage() && update.message.hasText()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true
            if (Days.values().map { d -> d.ru }.contains(update.message.text)) {
                val api = HTTPAPI()
                val day = Days.values().find { day -> day.ru == update.message.text }!!
                val lessons = api.scheduleForTheDay(day)
                message.text = lessons.joinToString("\n")
            } else {
                message.text = "invalid command"
            }
            execute(message)
        }
    }
}