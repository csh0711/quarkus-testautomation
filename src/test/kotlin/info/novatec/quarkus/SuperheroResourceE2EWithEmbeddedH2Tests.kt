package info.novatec.quarkus

import JsonMatcher.Companion.jsonEqualTo
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import javax.ws.rs.core.HttpHeaders.LOCATION
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
internal class SuperheroResourceE2EWithEmbeddedH2Tests {

    @Test
    fun `create and retrieve superhero`() {

        val inputJson = """
                { 
                  "name": "The Mocker", 
                  "location": "Mock City", 
                  "realName": "Monica Mock", 
                  "occupation": "Tester"
              }"""

        val locationUri = given()
            .contentType(APPLICATION_JSON)
            .body(inputJson)
            .`when`().post("superheroes")
            .then()
            .statusCode(201)
            .extract()
            .header(LOCATION)

        val outputJson = """{
                "id": ${extractId(locationUri)}, 
                "name": "The Mocker", 
                "location": "Mock City"
              }"""

        given()
            .`when`()[locationUri]
            .then()
            .statusCode(200)
            .contentType(APPLICATION_JSON)
            .body(jsonEqualTo(outputJson))
    }

    private fun extractId(locationUri: String) = locationUri.substring(locationUri.lastIndexOf('/') + 1);
}
