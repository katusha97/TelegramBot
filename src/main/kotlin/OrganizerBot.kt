import commands.*
import commands.Homework
import commands.Timetable
import kotlinx.coroutines.runBlocking
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import models.Course
import models.HomeworkToSend
import models.SpecialCourse
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.HashMap

class OrganizerBot : TelegramLongPollingCommandBot() {
    private val lastFiles = HashMap<String, String>()
    private val listOfSubjectForHW = listOf("Алгоритмы ДЗ", "Матлогика ДЗ", "Формальные языки ДЗ", "С++ ДЗ",
        "Матстат ДЗ", "Типы в ЯП ДЗ")
    private val listOfSpecialSubject = listOf("С++", "Матстат", "Типы в ЯП")
    private val mapOfCommandName = HashMap<String, List<String>>()

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
        register(HW())
        register(Perfreport())
        register(HomeworkSubj())
        fillMap()
    }

    override fun processNonCommandUpdate(update: Update?) {
        val api: API = HTTPAPI()
        if (update!!.hasCallbackQuery()) {
            val (commandName, data) = update.callbackQuery.data.split("/")
            if (commandName == "perfreport") {
                val subject = Subject.values().find { v -> v.name == data }!!
                runBlocking {
                    val perfreport = api.getPerfreport(update.callbackQuery.message.messageId.toString(), subject)
                    val edit = EditMessageText.builder()
                        .chatId(update.callbackQuery.message.chatId.toString())
                        .messageId(update.callbackQuery.message.messageId)
                        .text(perfreport.link)
                        .build()
                    execute(edit)
                }
            } else if (commandName == "homework_subj") {
                val subject = Subject.values().find { v -> v.name == data }!!
                runBlocking {
                    val homework = api.getHW(update.callbackQuery.message.messageId.toString(), subject)
                    val edit = EditMessageText.builder()
                        .chatId(update.callbackQuery.message.chatId.toString())
                        .messageId(update.callbackQuery.message.messageId)
                        .text(homework.toString())
                        .parseMode("MarkdownV2")
                        .build()
                    execute(edit)
                }
            }
        }
        else if (update.hasMessage() && update.message.hasDocument()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true

            val keyboardMarkup = ReplyKeyboardMarkup()
            val keyboard: MutableList<KeyboardRow> = mutableListOf()
            val row1 = KeyboardRow()
            val row2 = KeyboardRow()
            for (i in 0 until listOfSubjectForHW.size / 2) {
                row1.add(listOfSubjectForHW[i])
            }
            for (i in listOfSubjectForHW.size / 2 until listOfSubjectForHW.size) {
                row2.add(listOfSubjectForHW[i])
            }

            keyboard.add(row1)
            keyboard.add(row2)

            keyboardMarkup.keyboard = keyboard
            message.replyMarkup = keyboardMarkup
            keyboardMarkup.oneTimeKeyboard = true
            message.text = "Выберите предмет"
            execute(message)
            val fileId = update.message.document.fileId // fileUniqueId?
            lastFiles[update.message.chatId.toString()] = fileId
        } else if (update.hasMessage() && update.message.hasText()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.parseMode = "MarkdownV2"
            message.disableWebPagePreview = true
            val currCommand = update.message.text
            System.err.println(currCommand)
            val day = DayOfWeek.values().find { d ->
                d.getDisplayName(TextStyle.FULL, Locale.US) == update.message.text
            }
            if (day != null) {
                val lessons = api.scheduleForTheDay(message.chatId, day)
                message.text = lessons.lessons.joinToString("\n")
            } else {
                when {
                    currCommand.contains('/') -> {
                        message.text = api.scheduleOFCourse(
                            message.chatId,
                            Course(mapOfCommandName[currCommand]!![0], mapOfCommandName[currCommand]!![1].toInt())
                        )
                    }
                    currCommand.contains("ДЗ") -> {
                        message.text = api.sendHW(
                            message.chatId,
                            HomeworkToSend(lastFiles[message.chatId]!!, mapOfCommandName[currCommand]!![0])

                        )
                    }
                    currCommand == "Специализация" -> {
                        val keyboardMarkup = ReplyKeyboardMarkup()
                        val keyboard: MutableList<KeyboardRow> = mutableListOf()
                        val row = KeyboardRow()
                        for (s in listOfSpecialSubject) {
                            row.add(s)
                        }

                        keyboard.add(row)

                        keyboardMarkup.keyboard = keyboard
                        message.replyMarkup = keyboardMarkup
                        keyboardMarkup.oneTimeKeyboard = true
                        message.text = "Выберите предмет"
                        execute(message)
                    }
                    currCommand == "С++" -> message.text = api.scheduleOFSpecialCourse(message.chatId, SpecialCourse("C++"))
                    currCommand == "Матстат" -> message.text = api.scheduleOFSpecialCourse(message.chatId, SpecialCourse("matstat"))
                    currCommand == "Типы в ЯП" -> message.text = api.scheduleOFSpecialCourse(message.chatId, SpecialCourse("type"))
                    else -> message.text = "invalid command"
                }
            }
            execute(message)
        }
    }

    private fun fillMap() {
        mapOfCommandName["Типы в ЯП ДЗ"] = listOf("type")
        mapOfCommandName["Матстат ДЗ"] = listOf("matstat")
        mapOfCommandName["С++ ДЗ"] = listOf("C++")
        mapOfCommandName["Формальные языки ДЗ"] = listOf("formalLang")
        mapOfCommandName["Алгоритмы ДЗ"] = listOf("algorithm")
        mapOfCommandName["Матлогика ДЗ"] = listOf("mathlogic")
        mapOfCommandName["Формальные языки/Вербицкая"] = listOf("formalLang", "2")
        mapOfCommandName["Формальные языки/Халанский"] = listOf("formalLang", "1")
        mapOfCommandName["Алгоритмы/Лапенок"] = listOf("algorithm", "2")
        mapOfCommandName["Алгоритмы/Мишунин"] = listOf("algorithm", "1")
        mapOfCommandName["Матлогика/Халанский"] = listOf("mathlogic", "2")
        mapOfCommandName["Матлогика/Жаворонков"] = listOf("mathlogic", "1")
        mapOfCommandName["C++"] = listOf("C++")
        mapOfCommandName["Матстат"] = listOf("matstat")
        mapOfCommandName["Типы в ЯП"] = listOf("type")
    }
}