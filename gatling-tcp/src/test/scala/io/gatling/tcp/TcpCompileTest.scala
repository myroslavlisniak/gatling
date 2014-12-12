package io.gatling.tcp

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.tcp.Predef._

class TcpCompileTest extends Simulation {

  val tcpConfig = tcp.address("127.0.0.1").port(4800)
  val scn = scenario("Tcp")
    .exec(tcp("Connect").connect())
    .pause(1)
    .repeat(2, "i") {
    exec(tcp("Say Hello Tcp")
      .sendText( """{"text": "Hello, I'm ${id} and this is message ${i}!"}""")).pause(1)
  }

  setUp(scn.inject(rampUsers(100) over 10)).protocols(tcpConfig)


}
