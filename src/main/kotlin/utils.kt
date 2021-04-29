import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun createSubjectKeyboard(command: String): ReplyKeyboard {
    val keyboardMarkup = InlineKeyboardMarkup()
    val keyboard = Subject.values().map { s ->
        listOf(
            InlineKeyboardButton.builder()
                .callbackData(command + "/" + s.name)
                .text(s.subjectName)
                .build()
        )
    }
    keyboardMarkup.keyboard = keyboard
    return keyboardMarkup
}