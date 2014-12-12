package io.gatling.tcp

import io.gatling.core.scenario.{Scenario, Simulation}
import io.gatling.core.structure.PopulatedScenarioBuilder
import io.gatling.tcp.Predef._

class TcpCompileTest extends Simulation {

  val tcpConfig = tcp.address("127.0.0.1").port(4800)

  setUp().protocols(tcpConfig)




}
