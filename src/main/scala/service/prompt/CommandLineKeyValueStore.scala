package service.prompt

import com.twitter.util.{Await, Future}
import service.KeyValueStoreService

import scala.annotation.tailrec

/**
  * Created by cvu on 4/10/19.
  */
class CommandLineKeyValueStore(keyValueStoreService: KeyValueStoreService[Future, Array[Byte]]) {

  def start(): Unit = {

    introduction()

    @tailrec
    def callTillDone(): Unit = {
      printCommandPrompt()

      val commandValue = scala.io.StdIn.readInt()

      commandValue match {
        case 1 => printSetAValuePrompt()
          callTillDone()
        case 2 => printGetAValuePrompt()
          callTillDone()
        case 3 => println("good bye!")
        case invalidNumber => println("Sorry buddy, " + invalidNumber + " is not a valid selection, try again?")
          callTillDone()
      }

    }

    callTillDone()
  }


  def introduction(): Unit = {
    println("Hello my friend, welcome to the best distributed system ever!")
  }


  def printCommandPrompt(): Unit = {
    println("What would you like to do?")
    println("1. Set a value")
    println("2. Get a value")
    println("3. Exit")
  }

  def printGetAValuePrompt(): Unit = {
    println("Cool! Type me the key and I shall print out the value!")
    val key = scala.io.StdIn.readLine().trim
    if (key.isEmpty) {
      println("Empty response given! Please try again!")
    } else {
      val value = keyValueStoreService.get(key)
      value map {
        case Some(v) => println("Value is " + new String(v))
        case None    => println("Got a miss on the key value store!")
      }

      Await.result(value)
    }
  }

  def printSetAValuePrompt(): Unit = {
    println("Cool! Give me the key and value ie \"Foo=Bar\" ")
    val response = scala.io.StdIn.readLine().split('=').map(_.trim).filterNot(_.isEmpty)

    if (response.length == 2) {
      Await.result(keyValueStoreService.set(response.head, response(1).getBytes))
    } else {
      println("Invalid response, please try again!")
    }
  }

}
