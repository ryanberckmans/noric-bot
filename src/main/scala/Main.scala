
import scala.compiletime.constValue
import scala.util.Random
import scala.annotation.tailrec
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit = {
    import given NoricMud._
    val rand = scala.util.Random(System.nanoTime())
    val namesQueue = scala.collection.mutable.Queue(rand.shuffle(names): _*)
    val config = Config.default
    val numBots = Try(args(0).toInt).toOption.getOrElse(5)
    println(s"starting $numBots bots on ${config.serverHostname}:${config.serverPort}")
    Bot.runParallel[NoricMud](
      config,
      nextName = namesQueue.dequeue(),
      numBots,
    ).foreach(_.join())
  }
}
