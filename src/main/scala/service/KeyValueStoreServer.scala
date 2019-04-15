package service

import java.nio.ByteBuffer

import com.twitter.util.Future
import thrift.NonValue.KeyNotFound
import thrift.Response.NonValue
import thrift.{KeyValueService, Response}

/**
  * Created by cvu on 4/14/19.
  */
class KeyValueStoreServer(keyV: KeyValueStoreService[Future, Array[Byte]]) extends KeyValueService.MethodPerEndpoint {

  override def getValue(key: String): Future[Response] = {
    Future.value(NonValue(KeyNotFound))
  }

  override def setValue(key: String, data: ByteBuffer): Future[Unit] = Future.Unit
}
