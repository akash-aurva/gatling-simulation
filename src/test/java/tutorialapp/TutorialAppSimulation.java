package tutorialapp;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.concurrent.ThreadLocalRandom;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

/**
 * This sample is based on our official tutorials:
 * <ul>
 * <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling
 * quickstart tutorial</a>
 * <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling
 * advanced tutorial</a>
 * </ul>
 */
public class TutorialAppSimulation extends Simulation {

        FeederBuilder<String> feeder = csv("search.csv").random();

        ChainBuilder search = feed(feeder)
                        .exec(http("Search Tutorials")
                                        .get("/tutorials?title=#{searchCriterion}")
                                        .check(status().is(200)))
                        .pause(1);

        // repeat is a loop resolved at RUNTIME
        ChainBuilder browse =
                        // Note how we force the counter name, so we can reuse it
                        repeat(4, "i").on(
                                        exec(
                                                        http("Page #{i}")
                                                                        .get("/tutorials/#{i}")
                                                                        .check(status().is(200)))
                                                        .pause(1));

        ChainBuilder edit = exec(http("Update")
                        .put("/tutorials/" + ThreadLocalRandom.current().nextInt(4) + 1)
                        .asJson()
                        .body(StringBody("{\"published\":true}"))
                        .check(status().is(200)));

        HttpProtocolBuilder httpProtocol = http
                        .baseUrl("http://ec2-43-205-199-146.ap-south-1.compute.amazonaws.com:8080/api")
                        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .acceptEncodingHeader("gzip, deflate, br")
                        .userAgentHeader(
                                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

        ScenarioBuilder users = scenario("Users").exec(search, browse);
        ScenarioBuilder admins = scenario("Admins").exec(search, browse, edit);

        {
                setUp(
                                users.injectOpen(rampUsers(10).during(10)),
                                admins.injectOpen(rampUsers(2).during(10))).protocols(httpProtocol);
        }
}
