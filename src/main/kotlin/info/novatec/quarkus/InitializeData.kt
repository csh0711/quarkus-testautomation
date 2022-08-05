package info.novatec.quarkus

import io.quarkus.runtime.Startup
import javax.enterprise.context.ApplicationScoped

/**
 * Startup bean that might be used to automatically create a sample
 * list of [Superhero]s after application start.
 */
@Startup
@ApplicationScoped
class InitializeData {
    private val superheroService: SuperheroService

    constructor(superheroService: SuperheroService) {
        this.superheroService = superheroService
        // initialize() // uncomment this line to create a list of Superheros after starting the app
    }

    private fun initialize() =
        listOf(
            Superhero(
                name = "Spider-Man",
                location = "New York",
                realName = "Peter Parker",
                occupation = "Freelance photographer"
            ),
            Superhero(
                name = "Black Widow",
                location = "Volgograd",
                realName = "Natasha Romanoff",
                occupation = "Spy"
            ),
            Superhero(
                name = "Black Panther",
                location = "Wakanda",
                realName = "T'Challa",
                occupation = "King and Chieftain of Wakanda"
            ),
            Superhero(
                name = "Batman",
                location = "Gotham City",
                realName = "Bruce Wayne",
                occupation = "Businessman"
            ),
            Superhero(
                name = "Superman",
                location = "Metropolis",
                realName = "Clark Kent",
                occupation = "Reporter for the Daily Planet"
            )
        ).forEach {
            superheroService.addSuperhero(it)
        }
}