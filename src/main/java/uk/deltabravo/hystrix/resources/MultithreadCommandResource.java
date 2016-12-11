package uk.deltabravo.hystrix.resources;

import uk.deltabravo.hystrix.executors.MultiThreadCommandExecutor;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * Created by dennis on 01/12/2016.
 */
@Path("/api/v1")
public class MultithreadCommandResource
{

    @Setter
    private MultiThreadCommandExecutor commandExecutor;

    @GET
    @Path("/multi-commands")
    @Produces(MediaType.APPLICATION_JSON)
    public void executeMultiCommand(@NotNull @QueryParam("isolation") String isolation, @NotNull @QueryParam
            ("timeout") int timeout, @NotNull @QueryParam("threads") int maxThreads, @NotNull @QueryParam("interval")
            int execInterval, @Suspended final AsyncResponse
            response) throws Exception
    {
        new Thread(() -> response.resume(commandExecutor.execute(
                HystrixCommandProperties.ExecutionIsolationStrategy.valueOf(isolation.toUpperCase()),
                timeout,
                maxThreads, execInterval))).start();
    }
}
