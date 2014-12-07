package io.gatling.tcp

/**
 * Created by Slavik on 07.12.2014.
 */
object Predef {

  val tcp = TcpProtocolBuilderAddressStep

  implicit def tcpBuilderToProtocol(builder : TcpProtocolBuilder) : TcpProtocol = builder.build()
}
