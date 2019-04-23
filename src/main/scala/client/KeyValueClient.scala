package client

import com.twitter.finagle.Thrift
import com.twitter.util.Future
import service.KeyValueInterface
import thrift.KeyValueService.{GetValue, SetValue}
import thrift.NonValue.KeyNotFound
import thrift.Response.{DataFound, NonFound}
import thrift.{KeyValueService, StringData}

class KeyValueClient extends KeyValueInterface[Future, String] {

  val client: KeyValueService.ServicePerEndpoint = Thrift
    .client
    .servicePerEndpoint[KeyValueService.ServicePerEndpoint](dest = ":8080",
    label = "clientThrift")


  override def get(key: String): Future[Option[String]] = client.getValue(GetValue.Args(key)) map {
    case NonFound(KeyNotFound)    => None
    case DataFound(StringData(d)) => Some(d)
  }

  override def set(key: String, string: String): Future[Unit] = {
    client
      .setValue(
        SetValue.Args(
          key  = key,
          data = string
        )
      )
  }
}
