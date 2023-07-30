package simulations

import io.gatling.core.Predef.{constantUsersPerSec, _}
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps
class GetAPICallConstantUserRampUp extends Simulation
{
  val HttpRequest: HttpProtocolBuilder =
    http.proxy(Proxy("localhost", 8888))
      .baseUrl("https://reqres.in")
      .header("Accept", "application/json")


  //Scenario
  val scn: ScenarioBuilder = scenario("First Project")
    .exec(http("Get User Request")
      .get("/api/users?page=2").check(status is 200)
    )
  //SetUp

  setUp(
    scn.inject
    (nothingFor(5),

      constantUsersPerSec(10)during(10 seconds),
      incrementUsersPerSec(2)
        .times(5)
        .eachLevelLasting(2 seconds)
        .separatedByRampsLasting(10 seconds)
        .startingFrom(10) // Double

        //constantUsersPerSec(10)during(10 seconds),
      //rampUsersPerSec(1)to(5) during(20 seconds)

    ).protocols(HttpRequest)
  )
}