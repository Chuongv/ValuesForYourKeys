import service.KeyValueStoreService
import service.prompt.CommandLineKeyValueStore

/**
  * Created by cvu on 4/10/19.
  */
object Main extends App {

  val inMemoryKeyValueStore = new KeyValueStoreService[String] {

    val datastore = new scala.collection.mutable.HashMap[String, String]()

    override def get(key: String): Option[String] = datastore.get(key)

    override def set(key: String, value: String): Unit = datastore.update(key, value)
  }

  val prompter = new CommandLineKeyValueStore(inMemoryKeyValueStore)

  prompter.start()

}
