package org.jsonrpc

import json.ObjectAccessor

case class Notification[A: ObjectAccessor](method: String, parameters: Parameters[A])
