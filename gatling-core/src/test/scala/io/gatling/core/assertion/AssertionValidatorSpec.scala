/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.core.assertion

import io.gatling.core.config.GatlingConfiguration
import org.mockito.Mockito.when
import org.scalatest.{ FlatSpec, Matchers }
import org.scalatest.mock.MockitoSugar

import io.gatling.core.result._
import io.gatling.core.result.message.Status
import io.gatling.core.result.reader._
import io.gatling.core.util.StringHelper.RichString

class AssertionValidatorSpec extends FlatSpec with Matchers with MockitoSugar with AssertionSupport {

  GatlingConfiguration.setUpForTest()

  type RequestOrGroup = Either[RequestStatsPath, GroupStatsPath]

  private case class Stats(generalStats: GeneralStats,
                           requestName: String = "",
                           groupPath: List[String] = Nil,
                           status: Option[Status] = None) {

    def request = requestName.trimToOption
    def group = if (groupPath.nonEmpty) Some(Group(groupPath)) else None
  }

  private def mockDataReaderWithStats(metric: AssertionWithPathAndTarget,
                                      conditions: List[AssertionWithPathAndTarget => Assertion],
                                      stats: Stats*) = {
      def mockAssertion(dataReader: DataReader) = when(dataReader.assertions) thenReturn conditions.map(_(metric))

      def mockStats(stat: Stats, dataReader: DataReader) =
        when(dataReader.requestGeneralStats(stat.request, stat.group, stat.status)) thenReturn stat.generalStats

      def statsPaths = stats.map(stat => (stat.request, stat.group)).map {
        case (Some(request), group) => RequestStatsPath(request, group)
        case (None, Some(group))    => GroupStatsPath(group)
        case _                      => throw new AssertionError("Can't have neither a request or group stats path")
      }.toList

      def mockStatsPath(dataReader: DataReader) =
        when(dataReader.statsPaths) thenReturn statsPaths

    val mockedDataReader = mock[DataReader]

    mockAssertion(mockedDataReader)
    stats.foreach(mockStats(_, mockedDataReader))
    mockStatsPath(mockedDataReader)

    mockedDataReader
  }

  private def validateAssertions(dataReader: DataReader) =
    AssertionValidator.validateAssertions(dataReader).head.result

  "AssertionValidator" should "fail the assertion when the request path does not exist" in {
    val requestStats = Stats(GeneralStats.NoPlot, requestName = "bar")
    val reader1 = mockDataReaderWithStats(details("foo").requestsPerSec, List(_.is(100)), requestStats)
    validateAssertions(reader1) shouldBe false

    val groupStats = Stats(GeneralStats.NoPlot, groupPath = List("bar"))
    val reader2 = mockDataReaderWithStats(details("foo").requestsPerSec, List(_.is(100)), groupStats)
    validateAssertions(reader2) shouldBe false

    val requestAndGroupStats = Stats(GeneralStats.NoPlot, requestName = "baz", groupPath = List("bar"))
    val reader3 = mockDataReaderWithStats(details("baz").requestsPerSec, List(_.is(100)), requestAndGroupStats)
    validateAssertions(reader3) shouldBe false
  }
}
