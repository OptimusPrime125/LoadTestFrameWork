package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import javafx.util.Duration.seconds

class TestApiSimulationRampUpUser extends Simulation
{
  // Http Config
 val HttpRequest = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")

  //Scenario
  val scn = scenario("Ramp Up User ")
      .exec(http("Get User Request")
        .get("/api/users?page=2").check(status is 200)
      )
  //SetUp
  setUp(
    scn.inject(
 nothingFor(5),
      constantUsersPerSec(10) during(1 minus())
    )
  )
    .protocols(HttpRequest)

}