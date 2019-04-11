package service

/**
  * Created by cvu on 4/10/19.
  */
trait KeyValueStoreService[A] {

  def get(key: String): Option[A]

  def set(key: String, value: A): Unit
}
