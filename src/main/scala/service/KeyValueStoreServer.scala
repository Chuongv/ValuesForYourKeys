package service

import com.twitter.util.Future
import thrift.NonValue.KeyNotFound
import thrift.Response.{DataFound, NonFound}
import thrift.{KeyValueService, Response, StringData}

/**
  * Created by cvu on 4/14/19.
  */
class KeyValueStoreServer(keyV: KeyValueInterface[Future, String]) extends KeyValueService.MethodPerEndpoint {

  override def getValue(key: String): Future[Response] = {
    println("getValueForKey: " + key)
    keyV.get(key) map {
      case Some(b) => DataFound(StringData(b))
      case None    => NonFound(KeyNotFound)
    }
  }

  override def setValue(key: String, string: String): Future[Unit] = {
    keyV.set(key, string) flatMap { _ => Future.Unit}
  }
}
