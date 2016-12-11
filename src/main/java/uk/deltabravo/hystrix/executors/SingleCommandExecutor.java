package uk.deltabravo.hystrix.executors;

import uk.deltabravo.hystrix.commands.WebCommand;
import uk.deltabravo.hystrix.model.CommandResponse;
import uk.deltabravo.hystrix.model.WebCommandObject;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.Arrays;
import java.util.UUID;


/**
 * Created by dennis on 30/11/2016.
 */

public class SingleCommandExecutor extends CommandExecutor
{

    public CommandResponse execute(HystrixCommandProperties.ExecutionIsolationStrategy executionIsolationStrategy,
                                   int timeout)
    {
        String commandKey = String.format("%s-%s", executionIsolationStrategy.toString(), UUID.randomUUID().toString());
        HystrixRequestContext.initializeContext();
        WebCommand<WebCommandObject> testCommand;
        testCommand = new WebCommand(new WebCommandObject(), executionIsolationStrategy, timeout, commandKey);
        return new CommandResponse(Arrays.asList(testCommand.execute()),
                                   Arrays.asList(HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString()));

    }
}
