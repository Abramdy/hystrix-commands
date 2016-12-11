package uk.deltabravo.hystrix.resources;

import uk.deltabravo.hystrix.executors.SingleCommandExecutor;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by dennis on 30/11/2016.
 */
@Path("/api/v1")
public class SingleCommandResource
{
    @Setter
    private SingleCommandExecutor commandExecutor;

    @GET
    @Path("/command")
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeCommand(@NotNull @QueryParam("isolation") String isolation, @NotNull @QueryParam
            ("timeout") int timeout)
    {
        return Response.ok().entity(commandExecutor.execute(HystrixCommandProperties.ExecutionIsolationStrategy.valueOf
                (isolation.toUpperCase
                ()), timeout)).build();
    }
}
