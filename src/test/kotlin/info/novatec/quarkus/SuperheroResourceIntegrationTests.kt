package info.novatec.quarkus

import JsonMatcher.Companion.jsonEqualTo
import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
class SuperheroResourceIntegrationTests {

    @InjectMock
    private lateinit var service: SuperheroService

    @Test
    fun `GET superhero by id`() {
        every { service.findSuperhero(42L) } returns
                AnonymizedSuperhero(
                    id = 42L,
                    name = "Batman",
                    location = "Gotham City"
                )

        val expectedJson = """
            { 
              "id": 42, 
              "name": "Batman", 
              "location": "Gotham City"
            }
            """

        given()
            .`when`()["/superheroes/42"]
            .then()
            .statusCode(200)
            .contentType(APPLICATION_JSON)
            .body(jsonEqualTo(expectedJson))
    }
}