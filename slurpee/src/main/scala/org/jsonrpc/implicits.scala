package org.jsonrpc

import json._
import org.jsonrpc.polymorphic.Instance
import org.jsonrpc.struct._

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

  implicit def errorDataJson[E: JSONAccessor]: JSONAccessor[ErrorData[E]] =
    JSONAccessor.create({ r =>
      ErrorDataStruct(r).js
    }, { j =>
      j.to[ErrorDataStruct].r[E]
    })

  implicit def resultJson[A: ObjectAccessor]: JSONAccessor[Result[A]] =
    JSONAccessor.create({ r =>
      ResultStruct(r).js
    }, { j =>
      j.to[ResultStruct].r[A]
    })

  implicit def errorJson[E: ObjectAccessor]: JSONAccessor[Error[E]] =
    JSONAccessor.create({ e =>
      ErrorStruct(e).js
    }, { j =>
      j.to[ErrorStruct].r[E]
    })

  implicit def notificationJson[A: ObjectAccessor]: JSONAccessor[Notification[A]] =
    JSONAccessor.create({ n =>
      NotificationStruct(n).js
    }, { j =>
      j.to[NotificationStruct].r[A]
    })

}
