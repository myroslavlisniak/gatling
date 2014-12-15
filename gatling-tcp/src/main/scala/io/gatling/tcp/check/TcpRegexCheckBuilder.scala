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

import io.gatling.core.check._
import io.gatling.core.session._
import io.gatling.core.check.extractor.regex._

object TcpRegexCheckBuilder {

  def regex(expression: Expression[String], extender: Extender[TcpCheck, String]) =
    new TcpRegexCheckBuilder[String](expression, extender)
}

class TcpRegexCheckBuilder[X](private[tcp] val expression: Expression[String],
                              private[tcp] val extender: Extender[TcpCheck, String])(implicit groupExtractor: GroupExtractor[X])
    extends DefaultMultipleFindCheckBuilder[TcpCheck, String, CharSequence, X](
      extender,
      TcpCheckBuilders.PassThroughMessagePreparer) {

  def findExtractor(occurrence: Int) = expression.map(new SingleRegexExtractor(_, occurrence))

  def findAllExtractor = expression.map(new MultipleRegexExtractor(_))

  def countExtractor = expression.map(new CountRegexExtractor(_))
}
