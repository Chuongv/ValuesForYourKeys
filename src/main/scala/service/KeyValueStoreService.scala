package service

/**
  * Created by cvu on 4/10/19.
  */
trait KeyValueStoreService[F[_], A] {

  def get(key: String): F[Option[A]]

  def set(key: String, value: A): F[Unit]
}
