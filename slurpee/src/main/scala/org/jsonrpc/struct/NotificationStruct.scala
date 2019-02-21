package org.jsonrpc.struct

import json.{accessor, JObject, ObjectAccessor}
import org.jsonrpc.{jsonRPCVersion, Notification, Parameters}

@accessor case class NotificationStruct(
  jsonrpc: String,
  method: String,
  params: Parameters[JObject]
) {

  def r[A](implicit acc: ObjectAccessor[A]): Notification[A] =
    Notification(method, params.fold(Left(_), o => Right(acc.fromJSON(o))))

}

object NotificationStruct {

  def apply[A](n: Notification[A])(implicit acc: ObjectAccessor[A]) =
    NotificationStruct(jsonRPCVersion, n.method, n.parameters.fold(Left(_), o => Right(acc.createJSON(o))))

}
