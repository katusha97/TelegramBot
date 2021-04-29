package commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

class Help : BotCommand("/help", "Отправить домашку") {
    override fun execute(absSender: AbsSender?, user: User?, chat: Chat?, arguments: Array<out String>?) {
        val message = SendMessage()
        message.chatId = chat!!.id.toString()
        message.text = "Перед тобой бот органайзер, который может дать тебе акутальную информацию об учебном процессе.\n" +
                "Хочешь посмотреть расписание на сегодня - /timetable_today.\n" +
                "Хочешь посмотреть расписание на какой-то день недели - /timetable, а потом выбери день.\n" +
                "Для того, чтобы мы могли давать актуальную для тебя информацию тебе нужно указать по некоторым предметам" +
                "своего преподавателя. Для этого есть команда /timetable_by_subject\n" +
                "Нужно узнать домашку - /homework\n" +
                "Нужно узнать домашку по определенному предмету на завтра - /homework_subj, а потом выбери предмет\n" +
                "Хочешь отправить домашку по определенному предмету - /send_homework, потом загрузи файл, выбери необходимый предмет\n" +
                "Нужно посмотреть свою успеваемость - /perfreport, а потом выбери предмет\n" +
                "Еще ты можешь подписаться на ежедневные оповещания о домашке - /subscribe и /unsubscribe\n"
        absSender!!.execute(message)
    }
}

/*
Перед тобой бот органайзер, который может дать тебе акутальную информацию об учебном процессе.
Хочешь посмотреть расписание на сегодня - /timetable_today.
Хочешь посмотреть расписание на какой-то день недели - /timetable, а потом выбери день.
Для того, чтобы мы могли давать актуальную для тебя информацию тебе нужно указать по некоторым предметам
своего преподавателя. Для этого есть команда /timetable_by_subject
Нужно узнать домашку на завтра - /homework
Нужно узнать домашку по определенному предмету на завтра - /homework_subj, а потом выбери предмет
Хочешь отправить домашку по определенному предмету - /send_homework, потом загрузи файл, выбери необходимый предмет
Нужно посмотреть свою успеваемость - /perfreport, а потом выбери предмет
Еще ты можешь подписаться на ежедневные оповещания о домашке и расписании - /subscribe и /unsubscribe
 */