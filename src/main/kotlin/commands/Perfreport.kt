package commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import Subject

class Perfreport : BotCommand("perfreport", "Получение успеваемости по предмету") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.text = "Выберите предмет"
        message.replyMarkup = InlineKeyboardMarkup.builder().keyboardRow(
            listOf(
                InlineKeyboardButton.builder()
                    .callbackData("perfreport/" + Subject.ALGO.name)
                    .text(Subject.ALGO.subjectName)
                    .build()
            )
        ).build()
        absSender!!.execute(message)
    }
}