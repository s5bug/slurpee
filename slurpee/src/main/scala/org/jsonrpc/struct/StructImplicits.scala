package org.jsonrpc.struct

import json.ObjectAccessor

trait StructImplicits {

  implicit val request: ObjectAccessor[RequestStruct] = RequestStruct.acc
  implicit val rpcError: ObjectAccessor[RPCErrorStruct] = RPCErrorStruct.acc
  implicit val response: ObjectAccessor[ResponseStruct] = ResponseStruct.acc
  implicit val notification: ObjectAccessor[NotificationStruct] = NotificationStruct.acc

}
