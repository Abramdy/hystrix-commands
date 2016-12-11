package uk.deltabravo.hystrix.executors;

import uk.deltabravo.hystrix.commands.WebCommand;
import uk.deltabravo.hystrix.model.CommandResponse;
import uk.deltabravo.hystrix.model.WebCommandObject;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dennis on 01/12/2016.
 */
@AllArgsConstructor
@Slf4j
public class MultiThreadCommandExecutor extends CommandExecutor
{

    @Getter
    public static class ExecController
    {
        private List<WebCommandObject> objects = new ArrayList<>();
        private List<String> execResults = new ArrayList<>();
        @Setter
        private volatile int numRuns;
        @Setter
        private long execInterval = 0l;


        public void incrementRuns()
        {
            numRuns += 1;
        }
    }

    private void executeMultiCommands(
            final HystrixCommandProperties.ExecutionIsolationStrategy mode, final int timeout, final ExecController
            controller, final int numThreads, final String commandKey
    )
    {
        for (int i = 0; i < numThreads; i++)
        {
            (new Thread(() ->
                        {
                            HystrixRequestContext.initializeContext();
                            WebCommand<WebCommandObject> testCommand = new WebCommand<>(new WebCommandObject(), mode,
                                                                                        timeout, commandKey);
                            try
                            {
                                controller.getObjects().add(testCommand.execute());
                            }
                            catch (Exception e)
                            {
                                log.error("Uh-oh: {}, {}, {}", e.getClass().getName(), e.getMessage(), e.getCause());
                            }
                            finally
                            {

                                String executedCommandsAsString = HystrixRequestLog.getCurrentRequest()
                                                                                   .getExecutedCommandsAsString();
                                log.info("Le result: {}", executedCommandsAsString);
                                controller.incrementRuns();
                                controller.getExecResults().add(executedCommandsAsString);
                                synchronized (controller)
                                {
                                    if (controller.getNumRuns() == numThreads)
                                    {
                                        controller.notifyAll();
                                    }
                                }
                            }

                        })).start();

            if (controller.getExecInterval() > 0)
            {
                try
                {
                    Thread.sleep(controller.getExecInterval());
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Interrupted execution");
                }
            }
        }

    }


    @Override
    public CommandResponse execute(HystrixCommandProperties.ExecutionIsolationStrategy mode, int timeout)
    {
        return execute(mode, timeout, 10, 0l);
    }

    public CommandResponse execute(HystrixCommandProperties.ExecutionIsolationStrategy mode, int timeout, int
            numThreads, long execInterval)
    {
        String commandKey = String.format("%s-%s", mode.toString(), UUID.randomUUID().toString());
        ExecController execController = new ExecController();
        execController.setExecInterval(execInterval);
        executeMultiCommands(mode, timeout, execController, numThreads, commandKey);
        synchronized (execController)
        {
            try
            {
                execController.wait();

            }
            catch (InterruptedException e)
            {
                log.error("Uh-oh: {}, {}, {}", e.getClass().getName(), e.getMessage(), e.getCause());
            }
        }
        log.info("Chaos and disorder! My work here is done.");
        return new CommandResponse(execController.getObjects(), execController.getExecResults());
    }
}
