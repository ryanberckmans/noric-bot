
trait LoginCmds[T] {
  def loginCmds(name: String): Seq[String]
}
