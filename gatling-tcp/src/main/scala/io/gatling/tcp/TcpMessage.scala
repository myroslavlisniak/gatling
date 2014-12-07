package io.gatling.tcp

import io.gatling.core.session.Expression

sealed trait TcpMessage

case class TextTcpMessage(message : Expression[String]) extends TcpMessage
case class ByteTcpMessage(message : Expression[Array[Byte]]) extends TcpMessage
