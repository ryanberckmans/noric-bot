import scala.annotation.tailrec
import scala.util.Random

type NoricMud
object NoricMud {
  private val rareCmds = Set(
    "respawn",
    "ember",
    "aft",
    "respawn 0",
  )

  private val cmds: Array[String] = Array(
    "sneak",
    "af 1", // admin affect cmd
    "af 2", // admin affect cmd
    "af 3", // admin affect cmd
    "af 4", // admin affect cmd
    "af 5", // admin affect cmd
    "af 6", // admin affect cmd
    "af 7", // admin affect cmd
    "af 8", // admin affect cmd
    "af 9", // admin affect cmd
    "af 10", // admin affect cmd
    "af 11", // admin affect cmd
    "af 12", // admin affect cmd
    "af 13", // admin affect cmd
    "af 14", // admin affect cmd
    "af 15", // admin affect cmd
    "af 16", // admin affect cmd
    "af 17", // admin affect cmd
    "af 18", // admin affect cmd
    "aft", // admin tick affects cmd
    "close door",
    "close window",
    "search",
    "search",
    // "respawn",
    "respawn 0",
    "respawn", // note this cmd is hardcoded to be less frequent below
    "west",
    "west",
    "north",
    "north",
    "east",
    "east",
    "south",
    "south",
    "up",
    "up",
    "down",
    "down",
    "say for the overmind",
    // "cl live for the swarm",
    "look",
    "ember",
    "krkillrandom",
    "krkillrandom",
    "krkillrandom",
    "frfightrandom",
    "frfightrandom",
    "frfightrandom",
    "flee",
    "help",
    "glance A",
    "where",
    "who",
    "weapon",
    "rest",
    "stand",
    "stand",
    "stand",
    "meditate",
    "cooldowns",
    "quit",
    "quit",
    // DESTRUCTIVE BUILDER COMMANDS BELOW HERE
    //"buildwalk",
    //"dex" // destroy random exit
    //"dr" // destroy room
    //"tw" // toggle room is wilderness
  )

  // TODO env var SAVE_BOTS, if set will send "levelup to save bots"
  given as LoginCmds[NoricMud] = (name) => Seq(
    name + "\n",
    "yes-bot-secret-password-12893%$nlPeoxljKLJE\n", // send bot secret password if char is new so that bots will be spawned with realistic number of items/affects/etc.
    // "levelup\n", // levelup so bot is saved
  )

  given as NextCmds[NoricMud] = (rand) => Seq(randomCmd(rand) + "\n")

  @tailrec private def randomCmd(rand: Random): String = {
    val cmd = cmds(rand.nextInt(cmds.size))
    if (!rareCmds.contains(cmd) || rand.nextInt(100) < 98) cmd
    else randomCmd(rand)
  }
}
