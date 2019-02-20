package org.jsonrpc.struct

import json._
import org.jsonrpc.implicits._
import org.jsonrpc.{jsonRPCVersion, Response}

@accessor case class ResponseStruct(jsonrpc: String, result: JValue, error: RPCErrorStruct, id: Either[Int, String]) {

  def r[A: JSONAccessor, E: JSONAccessor] = Response(result.to[A], error.r[E], id)

}

object ResponseStruct {

  def apply[A: JSONAccessor, E: JSONAccessor](r: Response[A, E]): ResponseStruct =
    ResponseStruct(jsonRPCVersion, r.result.js, RPCErrorStruct(r.error), r.id)
}
