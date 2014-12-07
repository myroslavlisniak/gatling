package io.gatling.tcp

import io.gatling.core.session.Expression
import io.gatling.tcp.request.Tcp

object Predef {

  val tcp = TcpProtocolBuilderAddressStep

  implicit def tcpBuilderToProtocol(builder: TcpProtocolBuilder): TcpProtocol = builder.build()

  def tcp(requestName: Expression[String]) = new Tcp(requestName)
}
