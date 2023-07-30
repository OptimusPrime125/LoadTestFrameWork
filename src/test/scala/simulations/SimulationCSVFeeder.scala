package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class SimulationCSVFeeder extends Simulation
{
  // Http Config
  val HttpRequest: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in")
    .header("Accept", "application/json")
    .header("Authorization", "Bearer 6e05c602e989c9c506686019302447de4f6986b5e4d6efa9ba26296994095d39")

//CSV Feeder
  val csvFeeder = csv("src/test/resources/Data/userdata.csv").circular
  def getUser()=
    {
      repeat(3)
      {
        feed(csvFeeder)
          .exec(http("GET A LIST OF USER").get("/public/v2/users/${userid}")
            .check(status is 200)
          )
      }
    }

  //Scenario
  val scn: ScenarioBuilder =scenario("GET DATA OF USER").exec(getUser())

  //SetUp

  setUp(scn.inject(atOnceUsers(1))).protocols(HttpRequest)

}