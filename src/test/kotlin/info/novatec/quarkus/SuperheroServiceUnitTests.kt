package info.novatec.quarkus

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SuperheroServiceUnitTests {

    private val repository: SuperheroRepository = mockk()

    private val testee = SuperheroService(repository)

    @Test
    fun `get and anonymize superhero by id`() {
        every { repository.findById(42) } returns
                Superhero(
                    id = 42,
                    name = "Batman",
                    location = "Gotham City",
                    realName = "Bruce Wayne",
                    occupation = "Businessman"
                )

        val result = testee.findSuperhero(42)

        expectThat(result).isEqualTo(
            AnonymizedSuperhero(
                id = 42,
                name = "Batman",
                location = "Gotham City"
            )
        )
    }
}