import com.twitter.finagle.{ListeningServer, Thrift}
import com.twitter.util.Await
import data.MySqlDataStore
import service.KeyValueStoreServer

object Server extends App {
  println(args.mkString(","))

  val tableName        = args.head
  val port             = args(1)
  val replicationPort  = args(2).split(':')

  val mysqlDataStore = new MySqlDataStore(args.head)

  val server: ListeningServer = Thrift.server.serveIface(s":$port", new KeyValueStoreServer(mysqlDataStore, replicationPort))

  Await.ready(server)
}
