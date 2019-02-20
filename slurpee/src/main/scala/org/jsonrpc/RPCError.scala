package org.jsonrpc

import json.{accessor, JSONAccessor}

case class RPCError[E: JSONAccessor](code: RPCErrorCode, message: String, data: Option[E])

sealed trait RPCErrorCode {
  val code: Int
}

object RPCErrorCode {
  case object ParseError extends RPCErrorCode {
    override val code: Int = -32700
  }
  case object InvalidRequest extends RPCErrorCode {
    override val code: Int = -32600
  }
  case object MethodNotFound extends RPCErrorCode {
    override val code: Int = -32601
  }
  case object InvalidParameters extends RPCErrorCode {
    override val code: Int = -32602
  }
  case object InternalError extends RPCErrorCode {
    override val code: Int = -32603
  }
  case class ServerError(n: Int) extends RPCErrorCode {
    override val code: Int = -32000 - n
  }
  case class ApplicationError(code: Int) extends RPCErrorCode

  def apply(n: Int): RPCErrorCode = n match {
    case -32700 => ParseError
    case -32600 => InvalidRequest
    case -32601 => MethodNotFound
    case -32602 => InvalidParameters
    case -32603 => InternalError
    case c if c <= -32000 && c > -32100 => ServerError(-32000 - c)
    case c => ApplicationError(c)
  }
}
