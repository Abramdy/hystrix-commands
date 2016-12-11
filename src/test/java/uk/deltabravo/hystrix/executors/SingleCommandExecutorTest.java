package uk.deltabravo.hystrix.executors;

import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Assert;
import org.junit.Test;
import uk.deltabravo.hystrix.model.CommandResponse;

/**
 * Created by dennis on 11/12/2016.
 */
public class SingleCommandExecutorTest
{
    private SingleCommandExecutor commandExecutorUnderTest = new SingleCommandExecutor();
    @Test
    public void shouldReturn_AfterTimeoutWitThreadIsolation() throws Exception
    {
        CommandResponse r = commandExecutorUnderTest.execute(HystrixCommandProperties.ExecutionIsolationStrategy
                                                                     .THREAD, 10);
        Assert.assertTrue(r.getExecResults().get(0).contains("TIMEOUT"));
        //Verify that executed in timeout + some breathing room
        Assert.assertTrue(r.getWebCommandObjects().get(0).getExecutionTime() <= 20 );
    }

    @Test
    public void shouldReturn_AfterThreadUnblockedWithSemaphoreIsolation() throws Exception
    {
        CommandResponse r = commandExecutorUnderTest.execute(HystrixCommandProperties.ExecutionIsolationStrategy
                                                                     .SEMAPHORE, 10);
        Assert.assertTrue(r.getExecResults().get(0).contains("TIMEOUT"));
        //Verify that executed in more than or equal thread exec time
        Assert.assertTrue(r.getWebCommandObjects().get(0).getExecutionTime() >= 5000 );
    }

}