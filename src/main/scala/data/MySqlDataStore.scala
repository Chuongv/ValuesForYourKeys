package data

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql.Parameter._
import com.twitter.finagle.mysql.{Client, _}
import com.twitter.util.Future
import service.KeyValueInterface

/**
  * Created by cvu on 4/11/19.
  */
class MySqlDataStore(tableName: String) extends KeyValueInterface[Future,String] {

  private val client: Client with Transactions = Mysql.client
    .withCredentials("root","root")
    .withDatabase("distributedsystems")
    .newRichClient(":3306")


  private val selectQuery = s"SELECT data FROM $tableName WHERE data_key = ?"

  private val setQuery = "INSERT INTO datastore VALUES(?, ?) ON DUPLICATE KEY UPDATE data= ?"


  override def get(key: String): Future[Option[String]] = {

    val results = client.prepare(selectQuery).select[Option[String]](key) { row =>
      row.getString("data")
    }

    results.map(_.flatten.headOption)
  }

  override def set(key: String, value: String): Future[Unit] = {
    client.prepare(setQuery).apply(key, value, value) map { _ => ()
    }
  }
}
