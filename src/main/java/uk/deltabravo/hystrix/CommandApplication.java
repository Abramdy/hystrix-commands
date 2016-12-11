package uk.deltabravo.hystrix;


import uk.deltabravo.hystrix.config.AppConfiguration;
import uk.deltabravo.hystrix.executors.MultiThreadCommandExecutor;
import uk.deltabravo.hystrix.executors.SingleCommandExecutor;
import uk.deltabravo.hystrix.resources.MultithreadCommandResource;
import uk.deltabravo.hystrix.resources.SingleCommandResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;


/**
 * Created by dennis on 24/11/2016.
 */
public class CommandApplication extends Application<AppConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        new CommandApplication().run(args);
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception
    {
        final SingleCommandResource singleCommandResource = new SingleCommandResource();
        final MultithreadCommandResource multithreadCommandResource = new MultithreadCommandResource();
        singleCommandResource.setCommandExecutor(new SingleCommandExecutor());
        multithreadCommandResource.setCommandExecutor(new MultiThreadCommandExecutor());
        environment.jersey().register(singleCommandResource);
        environment.jersey().register(multithreadCommandResource);
    }

}
