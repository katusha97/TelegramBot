package commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender

class GroupDefinition : BotCommand("timetable_by_subject", "Запомнить у какого ты преподавателя") {

    private val listOfSubject = listOf<String>("Алгоритмы/Мишунин", "Алгоритмы/Лапенок",
        "Матлогика/Халанский", "Матлогика/Жаворонков", "Формальные языки/Халанский", "Формальные языки/Вербицкая",
        "Предмет по специализации")

    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.parseMode = "MarkdownV2"
        message.disableWebPagePreview = true

        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard: MutableList<KeyboardRow> = mutableListOf()
        val row1 = KeyboardRow()
        val row2 = KeyboardRow()
        for (i in 0 until listOfSubject.size / 2) {
            row1.add(listOfSubject[i])
        }
        for (i in listOfSubject.size / 2 until listOfSubject.size) {
            row2.add(listOfSubject[i])
        }

        keyboard.add(row1)
        keyboard.add(row2)

        keyboardMarkup.keyboard = keyboard
        message.replyMarkup = keyboardMarkup
        keyboardMarkup.oneTimeKeyboard = true
        message.text = "Выберите предмет"
        absSender!!.execute(message)
    }
}