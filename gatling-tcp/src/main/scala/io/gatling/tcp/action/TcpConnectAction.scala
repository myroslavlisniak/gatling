package io.gatling.tcp.action

import akka.actor.ActorRef
import io.gatling.core.action.{Failable, Interruptable}
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.validation.Validation


class TcpConnectAction(requestName : Expression[String]) extends Interruptable with Failable{
  /**
   * @return the next Action in the scenario workflow
   */
  override def next: ActorRef = ???

  /**
   * Core method executed when the Action received a Session message
   *
   * @param session the session of the virtual user
   * @return Nothing
   */

  override def executeOrFail(session: Session): Validation[_] = ???
}
