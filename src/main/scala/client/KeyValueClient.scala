package client

import java.nio.ByteBuffer

import com.twitter.finagle.Thrift
import com.twitter.util.Future
import service.KeyValueInterface
import thrift.KeyValueService.{GetValue, SetValue}
import thrift.NonValue.KeyNotFound
import thrift.Response.{DataFound, NonFound}
import thrift.{BinaryData, KeyValueService}

class KeyValueClient extends KeyValueInterface[Future, ByteBuffer] {

  val client: KeyValueService.ServicePerEndpoint = Thrift
    .client
    .servicePerEndpoint[KeyValueService.ServicePerEndpoint](dest = ":8080",
    label = "clientThrift")


  override def get(key: String): Future[Option[ByteBuffer]] = client.getValue(GetValue.Args(key)) map {
    case NonFound(KeyNotFound)    => None
    case DataFound(BinaryData(d)) => Some(d)
  }

  override def set(key: String, value: ByteBuffer): Future[Unit] = client
    .setValue(SetValue.Args(
    key = key,
    value))
}
