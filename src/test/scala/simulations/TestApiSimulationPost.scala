package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class TestApiSimulationPost extends Simulation
{

  //Config
  val HttpConfig: HttpProtocolBuilder = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json")


  //Scenario
  val Scenario = scenario("First Post Call")
      .exec(http("Create User")
        .post("/api/register")
        .body(RawFileBody("src/test/resources/Payload/createuser.json")).asJson
        .header("content-type","application/json")
        .check(status is 200)

    )

//Setup
    setUp(Scenario.inject(atOnceUsers(1))).protocols(HttpConfig)

}
