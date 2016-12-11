package uk.deltabravo.hystrix.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dennis on 24/11/2016.
 */

public abstract class CommandObject
{
    @Getter
    @Setter
    private boolean failure;

    public abstract void endExecution(long mutationTime);
}
