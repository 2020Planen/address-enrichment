package org.acme;

import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.acme.restclient.AddressData;
import org.acme.restclient.AddressService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
    @GET
    @Path("/api/hello2")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2() {
        return "hello";
    }
    
    
    @Inject
    @RestClient
    AddressService addressService;

    @GET
    @Path("/api/address/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public String query(@PathParam String query) {
        return addressService.getByQuery(query);
    }
}
