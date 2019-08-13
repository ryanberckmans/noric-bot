
class ClientSocket(config: Config) {
  import scala.util.Try
  import java.net.Socket
  import java.io.{PrintWriter, BufferedReader, InputStreamReader}
  import ClientSocket._

  private var socket: Option[Socket] =
    Try(new Socket(config.serverHostname, config.serverPort)).toOption
  private var out: Option[PrintWriter] =
    socket.flatMap(s => Try(PrintWriter(s.getOutputStream(), true)).toOption)
  private var in: Option[BufferedReader] = socket.flatMap(
    s => Try(BufferedReader(InputStreamReader(s.getInputStream()))).toOption)

  def isConnected: Boolean = socket.fold(false)(_.isConnected)

  def disconnect(): Unit = {
    out.map(_.close())
    out = None
    in.map(_.close())
    in = None
    socket.map(_.close())
    socket = None
  }

  def send(msg: String): Unit = {
    if (isConnected) out.foreach(o => {
      o.write(msg)
      if (o.checkError()) disconnect()
    })
  }

  private val arr = Array.ofDim[Char](1024*128)
  private val emptyMsg = MsgFromServer("")
  def receive(): ReceiveResult = {
    if (!isConnected) Disconnected
    else in.fold(Disconnected)(br => {
      if (br.ready()) {
        Try(br.read(arr, 0, arr.length)).toOption match {
          case Some(charCountRead) =>
            if (charCountRead < 0) {
              disconnect() // end of file if read() returns < 0 chars raed
              Disconnected
            } else if (charCountRead > 0) {
              MsgFromServer(arr.take(charCountRead).mkString)
            } else emptyMsg
          case _ => {
            disconnect() // exception during read()
            Disconnected
          }
        }
      } else emptyMsg
    })
  }
}

object ClientSocket {
  enum ReceiveResult {
    case Disconnected
    case MsgFromServer(msg: String)

  }
  export ReceiveResult._
}
