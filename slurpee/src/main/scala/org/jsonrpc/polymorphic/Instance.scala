package org.jsonrpc.polymorphic

sealed trait Instance[F[_]] {

  type Elem

  def elem: Elem
  def instance: F[Elem]

}

object Instance {

  def apply[F[_], A](myElem: A)(implicit myInstance: F[A]): Instance[F] = new Instance[F] {

    override type Elem = A

    override def elem: A = myElem
    override def instance: F[A] = myInstance

  }

}
