import client.KeyValueClient
import data.MySqlDataStore
import service.prompt.CommandLineKeyValueStore

/**
  * Created by cvu on 4/10/19.
  */
object Client extends App {

  val mysqlDataStore = new MySqlDataStore

  val prompter = new CommandLineKeyValueStore(new KeyValueClient)

  prompter.start()

}
