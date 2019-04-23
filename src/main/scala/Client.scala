import client.KeyValueClient
import service.prompt.CommandLineKeyValueStore

/**
  * Created by cvu on 4/10/19.
  */
object Client extends App {

  val prompter = new CommandLineKeyValueStore(new KeyValueClient)

  prompter.start()

}
