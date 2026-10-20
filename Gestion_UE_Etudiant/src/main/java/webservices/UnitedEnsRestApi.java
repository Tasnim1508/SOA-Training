
package webservices;

import entities.UniteEnseignement;
import filtres.Secured;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ue-v2")
public class UnitedEnsRestApi {

    static UniteEnseignementBusiness helper =
            new UniteEnseignementBusiness();
    @Secured
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListeUe() {
        return Response
                .status(Response.Status.OK)
                .entity(helper.getListeUE())
                .build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {

        if (helper.addUniteEnseignement(ue)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity("success")
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("erreur")
                    .build();
        }
    }
    @Secured
    @PUT
    @Path("/update/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUe(@PathParam("code") int code, UniteEnseignement ue) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(Response.Status.OK).entity("updated").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("UE introuvable").build();
    }

    @Secured
    @DELETE
    @Path("/delete/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(Response.Status.OK).entity("deleted").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("UE introuvable").build();
    }
}
