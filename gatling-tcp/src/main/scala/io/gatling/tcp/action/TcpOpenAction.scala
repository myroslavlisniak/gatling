package io.gatling.tcp.action

import akka.actor.ActorRef
import io.gatling.core.action.Interruptable
import io.gatling.core.session.Session


class TcpOpenAction extends Interruptable{
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
  override def execute(session: Session): Unit = ???
}
