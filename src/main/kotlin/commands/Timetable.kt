package commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class Timetable : BotCommand("timetable", "Расписание") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.parseMode = "MarkdownV2"
        message.disableWebPagePreview = true

        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard: MutableList<KeyboardRow> = mutableListOf()
        val row1 = KeyboardRow()
        row1.add(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.US))
        row1.add(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, Locale.US))
        row1.add(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, Locale.US))
        val row2 = KeyboardRow()
        row2.add(DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.US))
        row2.add(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.US))
        row2.add(DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.US))

        keyboard.add(row1)
        keyboard.add(row2)

        keyboardMarkup.keyboard = keyboard
        message.replyMarkup = keyboardMarkup
        message.text = "Выберите день"
        absSender!!.execute(message)
    }
}