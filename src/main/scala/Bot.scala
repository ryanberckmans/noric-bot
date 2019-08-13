import scala.annotation.tailrec
import scala.util.Random
import java.util.concurrent.atomic.AtomicInteger

import Bot._

class Bot[T: LoginCmds: NextCmds](
  val config: Config,
  val rand: Random,
  name: String,
) {
  import ClientSocket.{Disconnected, MsgFromServer}

  private val socket = ClientSocket(config)
  def isConnected = socket.isConnected
  def disconnect() = socket.disconnect()

  private var _tick: Long = 0
  def tick(): Seq[MsgFromServer] = {
    if (isConnected) socket.receive() match {
      case Disconnected => Seq.empty
      case m:MsgFromServer => {
        if (_tick < 1) the[LoginCmds[T]].loginCmds(name).foreach(socket.send(_))
        else the[NextCmds[T]].nextCmds(rand).foreach(socket.send(_))
        _tick += 1
        Seq(m)
      }
    } else Seq.empty
  }
}

object Bot {
  type MsgFromServer = ClientSocket.MsgFromServer

  @tailrec def tickUntilDisconnected[T](bot: Bot[T]): Unit = {
    bot.tick().foreach(m => if (bot.config.printBotOutput) print(m.msg))
    if (bot.isConnected) {
      Thread.sleep(bot.config.tickMillis)
      tickUntilDisconnected(bot)
    }
  }

  def runParallel[T: LoginCmds: NextCmds](
    config: Config,
    nextName: => String,
    numBots: Int,
    numConnected: AtomicInteger = AtomicInteger(),
  ): List[Thread] = {
    if (numBots < 1) Nil
    else {
      val name = nextName
      val t = Thread(() => {
        val rand = Random(System.nanoTime())
        Thread.sleep((numBots * 25).toLong) // sleep before connecting to avoid ddossing game with all parallel bots at once
        val bot = Bot(config, rand, name)
        println(s"${numConnected.incrementAndGet()} total connected")
        Bot.tickUntilDisconnected(bot)
        println(s"${numConnected.decrementAndGet()} total connected")
      })
      t.start()
      t :: runParallel(config, nextName, numBots - 1, numConnected)
    }
  }
}
