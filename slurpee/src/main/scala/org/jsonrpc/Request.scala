package org.jsonrpc

import implicits._
import json._
import org.jsonrpc.polymorphic.Instance

case class Request[A: ObjectAccessor](method: String, parameters: Parameters[A], id: Either[Int, String])
