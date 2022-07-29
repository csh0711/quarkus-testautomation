package info.novatec.quarkus

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@ApplicationScoped
class SuperheroRepository : PanacheRepository<Superhero>

@Entity
data class Superhero(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String,
    var location: String,
    var realName: String,
    var occupation: String
)
