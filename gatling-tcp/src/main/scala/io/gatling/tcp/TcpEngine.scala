package io.gatling.tcp

import java.net.InetSocketAddress
import java.util.concurrent.{Executors, TimeUnit}

import akka.actor.ActorRef
import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.akka.AkkaDefaults
import io.gatling.core.check.Check
import io.gatling.core.session.Session
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.channel.{Channels, ChannelPipeline, ChannelPipelineFactory}
import org.jboss.netty.channel.socket.nio.{NioWorkerPool, NioClientBossPool, NioClientSocketChannelFactory}
import org.jboss.netty.handler.codec.frame.{LengthFieldPrepender, LengthFieldBasedFrameDecoder}
import org.jboss.netty.handler.codec.string.{StringEncoder, StringDecoder}
import org.jboss.netty.util.{CharsetUtil, HashedWheelTimer}


object TcpEngine extends AkkaDefaults with StrictLogging{
  private var _instance: Option[TcpEngine] = None

  def start(): Unit = {
    if (!_instance.isDefined) {
      val client = new TcpEngine
      _instance = Some(client)
      system.registerOnTermination(stop())
    }
  }

  def stop(): Unit = {
    _instance.map { engine =>
      engine.nioThreadPool.shutdown()
    }
    _instance = None
  }

  def instance: TcpEngine = _instance match {
    case Some(engine) => engine
    case _            => throw new UnsupportedOperationException("HTTP engine hasn't been started")
  }
}
case class TcpTx(session: Session,
                  next : ActorRef,
                  start : Long,
                  check : Option[Check] = None,
                  protocol : TcpProtocol,
                  message : TcpMessage)

class TcpEngine {
  val numWorkers = Runtime.getRuntime.availableProcessors()
  val nettyTimer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS)
  val nioThreadPool = Executors.newCachedThreadPool
  val socketChannelFactory = new NioClientSocketChannelFactory(new NioClientBossPool(nioThreadPool, 1, nettyTimer, null),new NioWorkerPool(nioThreadPool,numWorkers))

  val prepender: LengthFieldPrepender = new LengthFieldPrepender(4)

  val stringDecoder: StringDecoder = new StringDecoder(CharsetUtil.UTF_8)

  val encoder: StringEncoder = new StringEncoder(CharsetUtil.UTF_8)

  def tcpClient(session: Session, protocol : TcpProtocol, listener : MessageListener) = {
    val bootstrap = new ClientBootstrap(socketChannelFactory)
    bootstrap.setPipelineFactory(new ChannelPipelineFactory {
      override def getPipeline: ChannelPipeline = {
         val pipeline = Channels.pipeline()
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(Short.MaxValue,0,4,0,4))
        pipeline.addLast("prepender", prepender)
        pipeline.addLast("decoder", stringDecoder)
        pipeline.addLast("encoder", encoder)
        pipeline.addLast("listener", listener)
        pipeline
      }
    })
    val channel = bootstrap.connect(new InetSocketAddress(protocol.address, protocol.port)).awaitUninterruptibly().getChannel
    (session, channel)
  }

  def startTcpTransaction(tx: TcpTx, actor : ActorRef) ={
    val listener = new MessageListener(actor)

    val (session, channel) = tcpClient(tx.session,tx.protocol, listener)
    channel.write(tx.message)
  }
}
