package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class GetRequestRampUp extends Simulation {
  // Http Config
  val HttpRequest: HttpProtocolBuilder =
    http.proxy(Proxy("localhost",8888))
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
      atOnceUsers(5),
      rampUsers(10) during (10 seconds)
    ).protocols(HttpRequest)
  )

}