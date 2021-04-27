class Parser {
    fun parse(string: String): Command {
        if (string.equals("start")) {
            return Command("start", ArrayList())
        } else if (string.startsWith("Расписание")) {
            return Command("Расписание", ArrayList())
        } else if (string.startsWith("Расписание на")) {
            return Command("Расписание на", arrayListOf(string.substringAfter("на ")))
        } else {
            return Command("", ArrayList())
        }
    }
}

class Command(val name: String, val args: List<String>) {
}