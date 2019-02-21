package org.jsonrpc.struct

import json._
import org.jsonrpc.{ErrorCode, ErrorData}

@accessor case class ErrorDataStruct(code: Int, message: String, data: Option[JValue]) {

  def r[E: JSONAccessor]: ErrorData[E] = ErrorData(ErrorCode(code), message, data.map(_.to[E]))

}

object ErrorDataStruct {

  def apply[E: JSONAccessor](r: ErrorData[E]): ErrorDataStruct =
    new ErrorDataStruct(r.code.code, r.message, r.data.map(_.js))

}
