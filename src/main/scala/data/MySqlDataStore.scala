package data

import java.nio.ByteBuffer

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql.Parameter._
import com.twitter.finagle.mysql.{Client, _}
import com.twitter.util.Future
import service.KeyValueInterface

/**
  * Created by cvu on 4/11/19.
  */
class MySqlDataStore extends KeyValueInterface[Future,ByteBuffer] {

  private val client: Client with Transactions = Mysql.client
    .withCredentials("root","root")
    .withDatabase("distributedsystems")
    .newRichClient(":3306")


  private val selectQuery = "SELECT data FROM datastore WHERE data_key = ?"

  private val setQuery = "INSERT INTO datastore VALUES(?, ?) ON DUPLICATE KEY UPDATE data= ?"


  override def get(key: String): Future[Option[ByteBuffer]] = {

    val results = client.prepare(selectQuery).select[Option[ByteBuffer]](key) { row =>
      row.getBytes("data").map(ByteBuffer.wrap)
    }

    results.map(_.flatten.headOption)
  }

  override def set(key: String, value: ByteBuffer): Future[Unit] =
    client.prepare(setQuery).apply(key, value.array(), value.array()) map { _ => ()}
}
