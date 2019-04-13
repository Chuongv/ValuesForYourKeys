import data.MySqlDataStore
import service.KeyValueStoreService
import service.prompt.CommandLineKeyValueStore

/**
  * Created by cvu on 4/10/19.
  */
object Main extends App {

  val mysqlDataStore = new MySqlDataStore

  val prompter = new CommandLineKeyValueStore(mysqlDataStore)

  prompter.start()

}
