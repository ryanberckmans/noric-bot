
// NextCmds is a typeclass to get the next cmds for a bot to
// send to the MUD. See NoricMud.scala for an example client.
trait NextCmds[T] {
  def nextCmds(rand: scala.util.Random): Seq[String]
}
