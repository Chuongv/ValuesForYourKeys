package service

import java.nio.ByteBuffer

import com.twitter.util.Future
import thrift.Response.NonFound
import thrift.{KeyValueService, NonValue, Response}

/**
  * Created by cvu on 4/14/19.
  */
class KeyValueStoreServer(keyV: KeyValueInterface[Future, Array[Byte]]) extends KeyValueService.MethodPerEndpoint {

  override def getValue(key: String): Future[Response] = {
    Future.value(NonFound(NonValue.KeyNotFound))
  }

  override def setValue(key: String, data: ByteBuffer): Future[Unit] = Future.Unit
}
