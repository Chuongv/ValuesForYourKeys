import com.twitter.finagle.{ListeningServer, Thrift}
import com.twitter.util.Await
import data.MySqlDataStore
import service.KeyValueStoreServer

object Server extends App {

  val mysqlDataStore = new MySqlDataStore

  val server: ListeningServer = Thrift.server.serveIface(":8080", new KeyValueStoreServer(mysqlDataStore))

  Await.ready(server)
}
