package org.jsonrpc

import json.{JSONAccessor, ObjectAccessor}

sealed trait Message[A]

sealed trait ClientMessage[A] extends Message[A] {

  val method: String
  val parameters: Option[Parameters[A]]

}

case class Notification[A: ObjectAccessor](method: String, parameters: Option[Parameters[A]]) extends ClientMessage[A]

case class Request[A: ObjectAccessor](method: String, parameters: Option[Parameters[A]], id: Either[Int, String])
    extends ClientMessage[A]

sealed trait ServerMessage[A] extends Message[A]

case class Result[A: JSONAccessor](result: A, id: Either[Int, String]) extends ServerMessage[A]

case class Error[E: JSONAccessor](error: ErrorData[E], id: Option[Either[Int, String]]) extends ServerMessage[E]
