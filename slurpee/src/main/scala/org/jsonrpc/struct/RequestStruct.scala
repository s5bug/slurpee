package org.jsonrpc.struct

import json._
import org.jsonrpc.implicits._
import org.jsonrpc.{jsonRPCVersion, Parameters, Request}

@accessor case class RequestStruct(
  jsonrpc: String,
  method: String,
  params: Parameters[JObject],
  id: Either[Int, String]
) {

  def r[A](implicit acc: ObjectAccessor[A]): Request[A] =
    Request(method, params.fold(Left(_), o => Right(acc.fromJSON(o))), id)
}

object RequestStruct {

  def apply[A](r: Request[A])(implicit acc: ObjectAccessor[A]): RequestStruct =
    RequestStruct(jsonRPCVersion, r.method, r.parameters.fold(Left(_), o => Right(acc.createJSON(o))), r.id)
}
