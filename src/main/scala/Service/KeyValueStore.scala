package Service

/**
  * Created by cvu on 4/10/19.
  */
trait KeyValueStore[A] {

  def get(key: String): Option[A]

  def set(key: String, value: A): Unit

}
