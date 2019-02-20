package org.jsonrpc.struct

import json._
import org.jsonrpc.{RPCError, RPCErrorCode}

@accessor case class RPCErrorStruct(code: Int, message: String, data: Option[JValue]) {

  def r[E: JSONAccessor]: RPCError[E] = RPCError(RPCErrorCode(code), message, data.map(_.to[E]))

}

object RPCErrorStruct {

  def apply[E: JSONAccessor](r: RPCError[E]): RPCErrorStruct =
    new RPCErrorStruct(r.code.code, r.message, r.data.map(_.js))

}
