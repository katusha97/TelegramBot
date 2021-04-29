import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import models.Notification

class HTTPServer(val bot: OrganizerBot) {
    fun run() {
        embeddedServer(Netty, port=8000) {
            install(ContentNegotiation) {
                json()
            }
            routing {
                get("/") {
                    call.respondText("Hello, World")
                }
                get("/send_alert") {
                    val notification = call.receive<Notification>()
                    bot.sendNotification(notification)
                    call.respondText("ok")
                }
            }
        }.start(wait = true)
    }
}