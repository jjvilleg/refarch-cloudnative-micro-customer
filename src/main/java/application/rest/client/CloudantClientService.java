package application.rest.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import javax.enterprise.context.Dependent;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Dependent
@RegisterRestClient
@Produces("application/json")
@RegisterProvider(UnknownCustomerExceptionManager.class)
@Path("/customers")
public interface CloudantClientService {
    
    /*
    GET /_design/username_searchIndex/_search/usernames?query=user
    */
    // ?query=usernames:user&include_docs=true
    @GET
    @Path("/_design/username_searchIndex/_search/usernames")
    @Produces("application/json")
    public javax.ws.rs.core.Response searchUsername(@QueryParam("query") String query, @QueryParam("include_docs") String include_docs) throws UnknownCustomerException;

    /*
    POST /_find
    */
    @POST
    @Path("/_find")
    @Produces("application/json")
    @Consumes("application/json")
    public javax.ws.rs.core.Response getUsername(JSONObject body) throws UnknownCustomerException;
}
