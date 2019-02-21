package org.jsonrpc.struct

import json._
import org.jsonrpc._
import org.jsonrpc.implicits._

sealed trait Struct[J[_]] {

  val jsonrpc: String

  def r[A](implicit acc: J[A]): Message[A]

}

sealed trait ClientStruct extends Struct[ObjectAccessor] {

  val method: String
  val params: Option[Parameters[JObject]]

}

@accessor case class NotificationStruct(
  jsonrpc: String,
  method: String,
  params: Option[Parameters[JObject]]
) extends ClientStruct {

  def r[A](implicit acc: ObjectAccessor[A]): Notification[A] =
    Notification(method, params.map(_.fold(Left(_), o => Right(acc.fromJSON(o)))))

}

object NotificationStruct {

  def apply[A](n: Notification[A])(implicit acc: ObjectAccessor[A]): NotificationStruct =
    NotificationStruct(jsonRPCVersion, n.method, n.parameters.map(_.fold(Left(_), o => Right(acc.createJSON(o)))))

}

@accessor case class RequestStruct(
  jsonrpc: String,
  method: String,
  params: Option[Parameters[JObject]],
  id: Either[Int, String]
) extends ClientStruct {

  def r[A](implicit acc: ObjectAccessor[A]): Request[A] =
    Request(method, params.map(_.fold(Left(_), o => Right(acc.fromJSON(o)))), id)
}

object RequestStruct {

  def apply[A](r: Request[A])(implicit acc: ObjectAccessor[A]): RequestStruct =
    RequestStruct(jsonRPCVersion, r.method, r.parameters.map(_.fold(Left(_), o => Right(acc.createJSON(o)))), r.id)
}

sealed trait ServerStruct extends Struct[JSONAccessor]

@accessor case class ResultStruct(jsonrpc: String, result: JValue, id: Either[Int, String]) extends ServerStruct {

  def r[A](implicit acc: JSONAccessor[A]): Result[A] =
    Result(result.to[A], id)

}

object ResultStruct {

  def apply[A](r: Result[A])(implicit acc: ObjectAccessor[A]): ResultStruct =
    ResultStruct(jsonRPCVersion, r.result.js, r.id)
}

@accessor case class ErrorStruct(jsonrpc: String, error: ErrorDataStruct, id: Option[Either[Int, String]])
    extends ServerStruct {

  def r[E](implicit acc: JSONAccessor[E]): Error[E] =
    Error(error.r[E], id)
}

object ErrorStruct {

  def apply[E](e: Error[E])(implicit acc: ObjectAccessor[E]): ErrorStruct =
    ErrorStruct(jsonRPCVersion, ErrorDataStruct(e.error), e.id)
}
