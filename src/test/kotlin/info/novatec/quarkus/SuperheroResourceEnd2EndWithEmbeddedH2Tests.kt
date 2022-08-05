package info.novatec.quarkus

import JsonMatcher.Companion.jsonEqualTo
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import javax.ws.rs.core.HttpHeaders.LOCATION
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource::class)
@TestProfile(H2TestProfiles::class)
internal class SuperheroResourceEnd2EndWithEmbeddedH2Tests {

    @Test
    fun `create and retrieve superhero`() {
        val inputJson = """
                {
                    "name": "Deadpool",
                    "location": "Sedona",
                    "realName": "Wade Winston Wilson",
                    "occupation": "Mercenary"
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
                "id": ${locationUri.extractId()}, 
                "name": "Deadpool", 
                "location": "Sedona"
              }"""

        given()
            .`when`()[locationUri]
            .then()
            .statusCode(200)
            .contentType(APPLICATION_JSON)
            .body(jsonEqualTo(outputJson))
    }

    private fun String.extractId() = substring(lastIndexOf('/') + 1);
}
