package org.jsonrpc

import json._
import org.jsonrpc.polymorphic.Instance
import org.jsonrpc.struct.{RPCErrorStruct, RequestStruct, ResponseStruct}

import scala.util.Try

object implicits extends struct.StructImplicits {

  implicit val jableJson: JSONAccessor[JAble] = JSONAccessor.create({ a =>
    a.instance.createJSON(a.elem)
  }, { j =>
    Instance(j)
  })

  implicit val jobjectJson: JSONAccessor[JObject] = JSONAccessor.create({ o =>
    o
  }, { j =>
    j.jObject
  })

  implicit def eitherJson[A, B](implicit a: JSONAccessor[A], b: JSONAccessor[B]): JSONAccessor[Either[A, B]] =
    JSONAccessor.create({
      case Left(ja) => a.createJSON(ja)
      case Right(jb) => b.createJSON(jb)
    }, { j =>
      Try(j.to[A]).map(Left(_)).getOrElse(Right(j.to[B]))
    })

  implicit def requestJson[A: ObjectAccessor]: JSONAccessor[Request[A]] =
    JSONAccessor.create({ r =>
      RequestStruct(r).js
    }, { j =>
      j.to[RequestStruct].r
    })

  implicit def rpcErrorJson[E: JSONAccessor]: JSONAccessor[RPCError[E]] =
    JSONAccessor.create({ r =>
      RPCErrorStruct(r).js
    }, { j =>
      j.to[RPCErrorStruct].r[E]
    })

  implicit def responseJson[A: JSONAccessor, E: JSONAccessor]: JSONAccessor[Response[A, E]] =
    JSONAccessor.create({ r =>
      ResponseStruct(r).js
    }, { j =>
      j.to[ResponseStruct].r[A, E]
    })

}
