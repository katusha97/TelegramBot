package subjects

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender

class Matlogica : BotCommand("Matlogika", "Выбери преподавателя") {

    private val teacherOne = "Жаворонков" // 0
    private val teacherTwo = "Халанский" // 1

    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.parseMode = "MarkdownV2"
        message.disableWebPagePreview = true

        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard: MutableList<KeyboardRow> = mutableListOf()
        val row = KeyboardRow()
        row.add(teacherOne)
        row.add(teacherTwo)

        keyboard.add(row)

        keyboardMarkup.keyboard = keyboard
        message.replyMarkup = keyboardMarkup
        keyboardMarkup.oneTimeKeyboard = true
        message.text = "Выберите преподавателя"
        absSender!!.execute(message)
    }
}