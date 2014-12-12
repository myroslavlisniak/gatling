package io.gatling.tcp.action

import akka.actor.ActorRef
import io.gatling.core.action.{Interruptable, Chainable, Failable}
import io.gatling.core.session.Session
import io.gatling.core.validation.Validation


class TcpSendAction extends Interruptable with Failable{
  override def executeOrFail(session: Session): Validation[_] = ???

  /**
   * @return the next Action in the scenario workflow
   */
  override def next: ActorRef = ???
}
