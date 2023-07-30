package TestCases

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class CHNReplace extends Simulation {

  // Http Config
  val HttpRequest: HttpProtocolBuilder = http.baseUrl("https://lims-layer.thyrocare.com")
    .header("Accept", "application/json")

  //Scenario
  val scn: ScenarioBuilder = scenario("First CHN Replace")
    .exec(http("Get First Request")
      .get("/api/v1/chn/chnClient?source=TAM03").check(status is 200)
      .check(jsonPath("$[0].suCode1").saveAs("AccCode"))

    )

  //SetUp
  setUp(scn.inject(atOnceUsers(1))).protocols(HttpRequest)

}
