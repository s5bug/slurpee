package org

import json.{JSONAccessor, ObjectAccessor}
import org.jsonrpc.polymorphic.Instance

package object jsonrpc {

  type JAble = Instance[JSONAccessor]

  // A: ObjectAccessor
  type Parameters[A] = Either[List[JAble], A]

  val jsonRPCVersion = "2.0"

}
