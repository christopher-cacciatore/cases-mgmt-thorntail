package fr.batigere.casesmgmt.rest;

import fr.batigere.casesmgmt.exceptions.UserNotFoundException;
import fr.batigere.casesmgmt.rest.dtos.Case;
import fr.batigere.casesmgmt.services.CasesService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cases")
@ApplicationScoped
public class CasesEndpoint {

    @Inject
    private CasesService casesService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Case> getAllCases() {
        return this.casesService.getAllCases();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCaseById(@PathParam("id") String id) {
        if (id == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        Case res = this.casesService.getCaseById(id);
        if (res == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(res).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewCase(@Valid Case pCase) {
        try {
            return Response.ok(this.casesService.createNewCase(pCase)).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCase(@PathParam("id") String id, @Valid Case pCase) {
        if (id == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        if (!id.equals(pCase.getId())) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        try {
            return Response.ok(this.casesService.updateCase(pCase)).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCase(@PathParam("id") String id) {
        boolean res = this.casesService.deleteCase(id);
        if (res) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
