package uk.deltabravo.hystrix.commands;

import uk.deltabravo.hystrix.model.CommandObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * Created by dennis on 24/11/2016.
 */
@Slf4j
public class WebCommand<T extends CommandObject> extends HystrixCommand<T>
{

    private final T subj;

    public WebCommand(T t, HystrixCommandProperties.ExecutionIsolationStrategy execStrategy, int timeout, String
            commandKey)
    {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("WebCommand")).andCommandKey
                (HystrixCommandKey.Factory.asKey(commandKey)).andCommandPropertiesDefaults
                (HystrixCommandProperties.Setter().withExecutionIsolationStrategy(execStrategy)
                                         .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds
                                (timeout).withRequestLogEnabled(true).withRequestCacheEnabled(true)
                                         .withCircuitBreakerEnabled(false)));
        this.subj = t;
    }

    @Override
    protected T run()
    {

        System.out.println("Let's start");
        long startTime = System.currentTimeMillis();
        try
        {
            TimeUnit.MILLISECONDS.sleep(5000);
        }
        catch (InterruptedException e)
        {
            log.warn("How dare you wake me up!?");
        }
        long mutationTime = System.currentTimeMillis() - startTime;
        subj.endExecution(mutationTime);
        log.info("My name is {} and I've been \"working very hard\" for {} ms.", Thread
                .currentThread().getName(), mutationTime);

        return subj;
    }

    @Override
    public T getFallback()
    {
        Throwable cause = this.getExecutionException();
        log.warn("Falling on my back and throwing a tantrum because {}!", cause.getMessage() != null ? cause
                .getMessage() : cause.getClass().getSimpleName());
        log.warn("Here's more details", cause);
        subj.setFailure(true);
        return subj;
    }
}
