
// LoginCmds is a typeclass to get the login cmds for a bot to
// login to a MUD. See NoricMud.scala for an example client.
trait LoginCmds[T] {
  def loginCmds(name: String): Seq[String]
}
