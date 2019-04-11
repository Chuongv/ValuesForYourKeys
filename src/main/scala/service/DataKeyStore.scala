package service

/**
  * Created by cvu on 4/10/19.
  */
trait DataKeyStore[A] {

  def get(key: String): Option[A]

  def set(key: String, value: A): Unit
}
