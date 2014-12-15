/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.tcp.check

import io.gatling.core.check.{ Extender, Preparer }
import io.gatling.core.validation.SuccessWrapper
import scala.concurrent.duration.FiniteDuration

object TcpCheckBuilders {

  def extender(timeout: FiniteDuration): Extender[TcpCheck, String] =
    wrapped => new TcpCheck(wrapped, timeout)

  val PassThroughMessagePreparer: Preparer[String, String] = (r: String) => r.success
}
