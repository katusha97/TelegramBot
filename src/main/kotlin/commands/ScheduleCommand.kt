package commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender

class ScheduleCommand : BotCommand("schedule", "Расписание") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.parseMode = "MarkdownV2"
        message.disableWebPagePreview = true

        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard: MutableList<KeyboardRow> = mutableListOf()
        val row1 = KeyboardRow()
        row1.add(Days.MONDAY.ru)
        row1.add(Days.TUESDAY.ru)
        row1.add(Days.WEDNESDAY.ru)
        val row2 = KeyboardRow()
        row2.add(Days.THURSDAY.ru)
        row2.add(Days.FRIDAY.ru)
        row2.add(Days.SATURDAY.ru)

        keyboard.add(row1)
        keyboard.add(row2)

        keyboardMarkup.keyboard = keyboard
        message.replyMarkup = keyboardMarkup
        message.text = "Выберите день"
        absSender!!.execute(message)
    }
}