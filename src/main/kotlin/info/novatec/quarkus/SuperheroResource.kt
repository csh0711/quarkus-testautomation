package info.novatec.quarkus

import info.novatec.quarkus.Superhero
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/superheroes")
class SuperheroResource(
    private val service: SuperheroService
) {
    @GET
    @Path("/")
    fun getAllSuperheroes(): Response =
        Response.ok(service.findSuperheroes()).build()

    @GET
    @Path("/{id}")
    fun getSingleSuperhero(@PathParam id: Long): Response =
        service.findSuperhero(id)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()

    @POST
    @Path("/")
    fun addSuperhero(superhero: Superhero, @Context uriInfo: UriInfo): Response {
        val id = service.addSuperhero(superhero).id
        val uri = uriInfo.absolutePathBuilder.path(id.toString()).build()
        return Response.created(uri).build()
    }
}
