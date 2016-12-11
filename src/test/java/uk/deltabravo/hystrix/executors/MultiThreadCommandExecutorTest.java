package uk.deltabravo.hystrix.executors;

import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Assert;
import org.junit.Test;
import uk.deltabravo.hystrix.model.CommandResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by dennis on 11/12/2016.
 */
public class MultiThreadCommandExecutorTest
{
    MultiThreadCommandExecutor multiThreadCommandExecutorUnderTest = new MultiThreadCommandExecutor();

    @Test
    public void shouldSeeRejections_ifNumberOfThreadsLargerThanMaxPo0lSize() throws Exception
    {
        //max pool size is 10 my default
        CommandResponse r = multiThreadCommandExecutorUnderTest.execute(HystrixCommandProperties
                                                                                .ExecutionIsolationStrategy.THREAD,
                                                                        1, 20, 0l);
        List<String> rejectedExecutions = r.getExecResults().stream().filter(k -> k.contains("THREAD_POOL_REJECTED")
                || k.contains
                ("FALLBACK_REJECTION")).collect(Collectors.toList
                ());
        Assert.assertTrue(!rejectedExecutions.isEmpty());
    }

    @Test
    public void shouldNotSeeRejections_ifNumberOfThreadsSmallerThanMaxPolSize() throws Exception
    {
        //max pool size is 10 my default
        CommandResponse r = multiThreadCommandExecutorUnderTest.execute(HystrixCommandProperties
                                                                                .ExecutionIsolationStrategy.THREAD,
                                                                        10, 9, 0l);
        List<String> rejectedExecutions = r.getExecResults().stream().filter(k -> k.contains("THREAD_POOL_REJECTED")
                || k.contains
                ("FALLBACK_REJECTION")).collect(Collectors.toList
                ());
        Assert.assertTrue(rejectedExecutions.isEmpty());
    }

    @Test
    public void shouldNotSeeRejections_ifNumberOfThreadsGreaterThanMaxPolSize_WithExecInterval() throws Exception
    {
        //max pool size is 10 my default
        CommandResponse r = multiThreadCommandExecutorUnderTest.execute(HystrixCommandProperties
                                                                                .ExecutionIsolationStrategy.THREAD,
                                                                        10, 15, 10l);
        List<String> rejectedExecutions = r.getExecResults().stream().filter(k -> k.contains("THREAD_POOL_REJECTED")
                || k.contains
                ("FALLBACK_REJECTION")).collect(Collectors.toList
                ());
        Assert.assertTrue(rejectedExecutions.isEmpty());
    }

}