package io.gatling.tcp

import io.gatling.core.akka.BaseActor
import io.gatling.core.result.message.{OK, Status}
import io.gatling.core.result.writer.DataWriterClient
import io.gatling.core.session.Session
import io.gatling.core.util.TimeHelper._
import org.jboss.netty.channel.Channel


class TcpActor extends BaseActor with DataWriterClient{

  override def receive: Receive = initialState

  val initialState : Receive = {
    case OnConnect(tx, channel, time) => context.become(connectedState(channel, tx))
    case _ => context.stop(self)
  }

  def connectedState (channel : Channel, tx : TcpTx) : Receive = {
    case Send(requestName, message, check, next, session) => {
      logger.debug(s"Sending message check on channel '$channel': $message")

      val now = nowMillis

      //      check match {
      //        case Some(c) =>
      //          // do this immediately instead of self sending a Listen message so that other messages don't get a chance to be handled before
      //          setCheck(tx, webSocket, requestName + " Check", c, next, session)
      //        case _ => reconciliate(next, session)
      //      }

      message match {
        case TextTcpMessage(text) => channel.write(text)
        case _ => logger.warn("Only text messages supported")
      }

      logRequest(session, requestName, OK, now, now)
    }
    case OnTextMessage(message, time) => {
      //do something, succeed pending checks
    }
  }

  private def logRequest(session: Session, requestName: String, status: Status, started: Long, ended: Long, errorMessage: Option[String] = None): Unit = {
    writeRequestData(
      session,
      requestName,
      started,
      ended,
      ended,
      ended,
      status,
      errorMessage)
  }
}
