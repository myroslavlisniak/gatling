package io.gatling.tcp


object Predef {

  val tcp = TcpProtocolBuilderAddressStep

  implicit def tcpBuilderToProtocol(builder : TcpProtocolBuilder) : TcpProtocol = builder.build()
}
