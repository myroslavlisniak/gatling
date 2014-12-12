package io.gatling.tcp

import akka.actor.ActorRef
import org.jboss.netty.channel.ChannelHandler.Sharable
import org.jboss.netty.channel.{MessageEvent, ChannelHandlerContext, SimpleChannelUpstreamHandler, ChannelUpstreamHandler}

@Sharable
class MessageListener(actor : ActorRef) extends SimpleChannelUpstreamHandler{
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {

  }
}
