package uk.deltabravo.hystrix.executors;

import uk.deltabravo.hystrix.model.CommandResponse;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by dennis on 01/12/2016.
 */
public  abstract class CommandExecutor
{
    public abstract CommandResponse execute(HystrixCommandProperties.ExecutionIsolationStrategy mode, int timeout);
}
