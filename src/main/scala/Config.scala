import scala.util.Try

case class Config(
  printBotOutput: Boolean = sys.env.get("PRINT_BOT_OUTPUT").isDefined,
  tickMillis: Long =
    sys.env.get("TICK_MILLIS").flatMap(s => Try(s.toLong).toOption).getOrElse(450),
  serverHostname: String = sys.env.getOrElse("HOST", "localhost"),
  serverPort: Int =
    sys.env.get("PORT").flatMap(s => Try(s.toInt).toOption).getOrElse(4000),
)

object Config {
  val default = Config()
}
