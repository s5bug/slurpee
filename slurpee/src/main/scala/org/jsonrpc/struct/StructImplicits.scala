package org.jsonrpc.struct

import json.ObjectAccessor

trait StructImplicits {

  implicit val request: ObjectAccessor[RequestStruct] = RequestStruct.acc
  implicit val errorData: ObjectAccessor[ErrorDataStruct] = ErrorDataStruct.acc
  implicit val result: ObjectAccessor[ResultStruct] = ResultStruct.acc
  implicit val error: ObjectAccessor[ErrorStruct] = ErrorStruct.acc
  implicit val notification: ObjectAccessor[NotificationStruct] = NotificationStruct.acc

}
