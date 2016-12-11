package uk.deltabravo.hystrix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

/**
 * Created by dennis on 28/11/2016.
 */
@Wither
@AllArgsConstructor
@NoArgsConstructor
public class WebCommandObject extends CommandObject
{
    @Getter

    private boolean executed;

    @Getter
    private int executions;

    @Getter
    @Setter
    private long executionTime;

    @Override
    public void endExecution(long mutationTime)
    {
        this.executed = true;
        this.executions += 1;
        this.executionTime = mutationTime;
    }
}
