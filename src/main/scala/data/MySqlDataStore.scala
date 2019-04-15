package data

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql.Parameter._
import com.twitter.finagle.mysql.{Client, _}
import com.twitter.util.Future
import service.KeyValueInterface

/**
  * Created by cvu on 4/11/19.
  */
class MySqlDataStore extends KeyValueInterface[Future,Array[Byte]] {

  private val client: Client with Transactions = Mysql.client
    .withCredentials("root","root")
    .withDatabase("distributedsystems")
    .newRichClient(":3306")


  private val selectQuery = "SELECT data FROM datastore WHERE data_key = ?"

  private val setQuery = "INSERT INTO datastore VALUES(?, ?) ON DUPLICATE KEY UPDATE data= ?"


  override def get(key: String): Future[Option[Array[Byte]]] = {

    val results = client.prepare(selectQuery).select[Option[Array[Byte]]](key) { row =>
      row.getBytes("data")
    }

    results.map(_.flatten.headOption)
  }

  override def set(key: String, value: Array[Byte]): Future[Unit] = client.prepare(setQuery).apply(key, value, value) map { _ => ()}
}
