package org.acme.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Set;


@RegisterRestClient
public interface AddressService {

    @GET
    @Path("/adresser?{query}")
    @Produces("application/json")
    String getByQuery (@PathParam String query);
}