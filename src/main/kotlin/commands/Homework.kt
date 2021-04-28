package commands

import HTTPAPI
import kotlinx.coroutines.runBlocking
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

class Homework: BotCommand("homework", "Получить список домашек на сегодня") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val api = HTTPAPI()
        runBlocking {
            val message = SendMessage()
            message.chatId = chat!!.id.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true
            val homeworks = api.getAllHW(message.chatId)
            message.text = homeworks.joinToString("\n")

            absSender!!.execute(message)
        }
    }
}