package info.novatec.quarkus

import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class SuperheroService(
    private val repository: SuperheroRepository
) {

    fun findSuperheroes(): List<AnonymizedSuperhero> = repository.listAll().map { it.anonymize() }

    fun findSuperhero(id: Long) = repository.findById(id)?.anonymize()

    @Transactional
    fun addSuperhero(superhero: Superhero): AnonymizedSuperhero {
        repository.persist(superhero)
        return superhero.anonymize()
    }

    private fun Superhero.anonymize() = AnonymizedSuperhero(
        id = this.id!!,
        name = this.name,
        location = this.location
    )
}

data class AnonymizedSuperhero(
    val id: Long,
    val name: String,
    val location: String
)
