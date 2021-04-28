package commands

import HTTPAPI
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

class ScheduleTodayCommand : BotCommand("schedule_today", "Расписание на сегодня") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.parseMode = "MarkdownV2"
        message.disableWebPagePreview = true

        val api = HTTPAPI()
        val lessons = api.scheduleForToday()
        message.text = lessons.joinToString("\n")
        absSender!!.execute(message)
    }
}