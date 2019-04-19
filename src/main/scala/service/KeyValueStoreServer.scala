package service

import java.nio.ByteBuffer

import com.twitter.util.Future
import thrift.NonValue.KeyNotFound
import thrift.Response.{DataFound, NonFound}
import thrift.{BinaryData, KeyValueService, Response}

/**
  * Created by cvu on 4/14/19.
  */
class KeyValueStoreServer(keyV: KeyValueInterface[Future, ByteBuffer]) extends KeyValueService.MethodPerEndpoint {

  override def getValue(key: String): Future[Response] = {
    println("getValueForKey: " + key)
    keyV.get(key) map {
      case Some(b) => DataFound(BinaryData(b))
      case None    => NonFound(KeyNotFound)
    }
  }

  override def setValue(key: String, data: ByteBuffer): Future[Unit] = {
    println("setValueForKey " + key)
    keyV.set(key, data) flatMap { _ => Future.Unit}
  }
}
