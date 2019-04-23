package service

import com.twitter.finagle.Thrift
import com.twitter.util.Future
import thrift.KeyValueService.SetValue
import thrift.NonValue.KeyNotFound
import thrift.Response.{DataFound, NonFound}
import thrift.{KeyValueService, Response, StringData}

/**
  * Created by cvu on 4/14/19.
  */
class KeyValueStoreServer(
                           keyV: KeyValueInterface[Future, String],
                           replicateTo: Seq[String]
                         ) extends KeyValueService.MethodPerEndpoint {

  private val clients = {
    replicateTo.map( port =>Thrift
    .client
    .servicePerEndpoint[KeyValueService.ServicePerEndpoint](dest = s":$port",
    label = s"clientThrift:$port")
    )
  }

  override def getValue(key: String): Future[Response] = {
    println("getValueForKey: " + key)
    keyV.get(key) map {
      case Some(b) => DataFound(StringData(b))
      case None    => NonFound(KeyNotFound)
    }
  }

  override def setValue(key: String, string: String): Future[Unit] = {
    keyV.set(key, string) flatMap { _ => Future.Unit} onSuccess{ _=>
      replicate(key,string)
      }
  }

  private def replicate(key: String, string: String): Unit = {
    clients.foreach { client =>
      client
        .setValue(
          SetValue.Args(
            key  = key,
            data = string
          )
        )
    }
  }

}
