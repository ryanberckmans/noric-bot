
trait NextCmds[T] {
  def nextCmds(rand: scala.util.Random): Seq[String]
}
