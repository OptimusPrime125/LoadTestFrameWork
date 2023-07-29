package simulations;
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestApiSimulation extends Simulation
{
  // Http Config
 val HttpRequest = http.baseUrl("https://reqres.in")
    .header("Accept","applicaion/json")

  //Scenario
  val scn = scenario("First Porject")
      .exec(http("Get User Request")
        .get("/api/users?page=2").check(status is 200)
      )
  //SetUp
  setUp(scn.inject(atOnceUsers(100))).protocols(HttpRequest)

}