package org.jsonrpc

import json.JSONAccessor

case class Response[A: JSONAccessor, E: JSONAccessor](result: A, error: RPCError[E], id: Either[Int, String])
