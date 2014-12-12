package io.gatling.tcp.action

import akka.actor.ActorRef
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.config.Protocols
import io.gatling.core.session.Expression
import io.gatling.tcp.TcpMessage


class TcpConnectActionBuilder(requestName : Expression[String]) extends  ActionBuilder{
  /**
   * @param next the Action that will be chained with the Action build by this builder
   * @param protocols the protocols configurations
   * @return the resulting Action actor
   */
  override def build(next: ActorRef, protocols: Protocols): ActorRef = ???
}

class TcpSendActionBuilder(requestName : Expression[String], message : Expression[TcpMessage]) extends ActionBuilder {
  /**
   * @param next the Action that will be chained with the Action build by this builder
   * @param protocols the protocols configurations
   * @return the resulting Action actor
   */
  override def build(next: ActorRef, protocols: Protocols): ActorRef = ???
}

class TcpDisconnectActionBuilder(requestName : Expression[String]) extends ActionBuilder {
  /**
   * @param next the Action that will be chained with the Action build by this builder
   * @param protocols the protocols configurations
   * @return the resulting Action actor
   */
  override def build(next: ActorRef, protocols: Protocols): ActorRef = ???
}