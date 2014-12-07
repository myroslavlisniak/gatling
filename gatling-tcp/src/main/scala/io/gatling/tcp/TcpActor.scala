package io.gatling.tcp

import io.gatling.core.akka.BaseActor
import io.gatling.core.result.writer.DataWriterClient


class TcpActor extends BaseActor with DataWriterClient{
  override def receive: Receive = ???
}
