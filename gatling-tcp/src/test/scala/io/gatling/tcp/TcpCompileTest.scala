package io.gatling.tcp

import io.gatling.core.scenario.Simulation
import io.gatling.tcp.Predef._

class TcpCompileTest extends Simulation{

  val tcpConfig = tcp.address("127.0.0.1").port(4800)
}
