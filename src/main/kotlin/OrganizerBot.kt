import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

class OrganizerBot: TelegramLongPollingBot() {
    override fun getBotToken(): String {
        return "1728118655:AAEQOKogNSnI0WgkTNzpbpufH6LXi6HP6lQ"
    }

    override fun getBotUsername(): String {
        return "JB_MSE_bot"
    }

    override fun onUpdateReceived(update: Update?) {
        if (update!!.hasMessage() && update.message.hasText()) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            val api = APIImpl()
            val parser = Parser()
            val request = update.message.text
            val command = parser.parse(request)
            val lessons = api.scheduleForTheDay("понедельник")
            when(command.name) {
                "start" -> message.text = lessons.toString()
//                "Paсписание" -> message.text = api.scheduleForWeek()
//                "Расписание на" -> message.text =
//                    if (command.args.size == 0 || command.args.size > 1) "invalid command"
//                    else if (command.args.get(0) == "сегодня") api.scheduleForToday()
//                    else api.scheduleForTheDay(command.args.get(0))
                else -> message.text = "invalid command"
            }
            try {
                execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }

}

fun main() {
    try {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(OrganizerBot())
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}