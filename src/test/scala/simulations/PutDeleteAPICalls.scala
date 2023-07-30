package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class PutDeleteAPICalls extends Simulation
{

  //Config
  val HttpConfig: HttpProtocolBuilder = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json")


  //Scenario
  val Scenario: ScenarioBuilder = scenario("PUT & Delete Call")
      .exec(http("Update User")
        .put("/api/users/2")
        .body(RawFileBody("src/test/resources/Payload/TestPut.json")).asJson
        .header("content-type","application/json")
        .check(status is 200)
    )

    .pause(1,5)

    .exec(http("Delete User")
      .delete("/api/users/2")
      .header("content-type", "application/json")
      .check(status is 204)
    )


//Setup
    setUp(Scenario.inject(atOnceUsers(1))).protocols(HttpConfig)

}
