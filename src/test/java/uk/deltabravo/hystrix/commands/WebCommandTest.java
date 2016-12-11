package uk.deltabravo.hystrix.commands;

import uk.deltabravo.hystrix.model.WebCommandObject;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by dennis on 24/11/2016.
 */
public class WebCommandTest
{
    public static final int TIMEOUT = 200;
    public static final int BUFFER = 75;

    public static final int THREAD_COMPLETION_TIME = 5000;

    @Test
    public void shouldReturnAfterTimeout_withThreadIsolation() throws Exception
    {
        String commandKey = String.format("%s-%s",
                                          HystrixCommandProperties.ExecutionIsolationStrategy.THREAD.toString(),
                                          UUID.randomUUID().toString());
        HystrixRequestContext.initializeContext();
        WebCommand<WebCommandObject> testCommand = new WebCommand(new WebCommandObject(), HystrixCommandProperties
                .ExecutionIsolationStrategy
                .THREAD, TIMEOUT, commandKey);
        long startTime = System.currentTimeMillis();
        WebCommandObject result = testCommand.execute();
        long execTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(execTime <= TIMEOUT + BUFFER);
        Assert.assertTrue(result.isFailure());
    }

    @Test
    public void shouldReturnAfterThreadCompletes_withSemaphoreIsolation() throws Exception
    {
        String commandKey = String.format("%s-%s",
                                          HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE.toString(),
                                          UUID.randomUUID().toString());
        HystrixRequestContext.initializeContext();
        WebCommand<WebCommandObject> testCommand = new WebCommand(new WebCommandObject(), HystrixCommandProperties
                .ExecutionIsolationStrategy
                .SEMAPHORE, TIMEOUT, commandKey);
        long startTime = System.currentTimeMillis();
        WebCommandObject result = testCommand.execute();
        long execTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(execTime >= THREAD_COMPLETION_TIME);
        Assert.assertTrue(result.isFailure());
    }
}

