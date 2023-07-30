package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class TestApiSimulationCocurrentRelation extends Simulation
{
  // Http Config
 val HttpRequest: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in")
    .header("Accept","application/json")
   .header("Authorization","Bearer 6e05c602e989c9c506686019302447de4f6986b5e4d6efa9ba26296994095d39")

  //Scenario
  val scn: ScenarioBuilder = scenario("First Project")
      .exec(http("Get User Request")
        .get("/public/v2/users").check(status is 200)
        .check(jsonPath("$[0].name").saveAs("Name"))
        .check(jsonPath("$[0].name").is("Ajit Shah"))
      )

  //SetUp
  setUp(scn.inject(atOnceUsers(10))).protocols(HttpRequest)

}