package info.novatec.quarkus

import info.novatec.quarkus.utils.JsonMatcher.Companion.jsonEqualTo
import info.novatec.quarkus.utils.PostgresTestcontainerTestProfiles
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import javax.ws.rs.core.HttpHeaders.LOCATION
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
@TestProfile(PostgresTestcontainerTestProfiles::class)
class SuperheroRegistryEnd2EndTests {

    @Test
    fun `create and retrieve superhero`() {
        val inputJson = """
              { 
                "name": "Batman", 
                "location": "Gotham City", 
                "realName": "Bruce Wayne", 
                "occupation": "Businessman"
              }
              """

        val locationUri = given()
            .contentType(APPLICATION_JSON)
            .body(inputJson)
            .`when`().post("superheroes")
            .then()
            .statusCode(201)
            .extract()
            .header(LOCATION)

        val outputJson = """
            {
              "id": ${locationUri.extractId()}, 
              "name": "Batman", 
              "location": "Gotham City"
            }
            """

        given()
            .`when`()[locationUri]
            .then()
            .statusCode(200)
            .contentType(APPLICATION_JSON)
            .body(jsonEqualTo(outputJson))
    }

    private fun String.extractId() = substring(lastIndexOf('/') + 1);
}
