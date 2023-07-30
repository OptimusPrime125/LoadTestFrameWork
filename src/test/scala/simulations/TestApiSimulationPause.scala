package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration.DurationInt

class TestApiSimulationPause extends Simulation
{
  // Http Config
 val HttpRequest: HttpProtocolBuilder = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")

  //Scenario
  val scn: ScenarioBuilder = scenario("First Porject")
      .exec(http("1st : Get User Request")
        .get("/api/users?page=2").check(status is 200))

    .pause(5)
    .exec(http("2nd : Get User Request")
      .get("/api/users?page=1").check(status is 200))

    .pause(1,10)
    //.pause(3000.millisecond)
    .exec(http("3Rd : Get User Request")
      .get("/api/users?page=3").check(status is 200))


  //SetUp
  setUp(scn.inject(atOnceUsers(100))).protocols(HttpRequest)

}