# hystrix-commands

The project shows differences between THREAD and SEMAPHORE isolation in Hystrix.

The functionality can be demonstrated using Unit Tests or Dropwizard web application.

2 controllers are provided:

-Single thread execution available via /api/v1/command/isolation=[isolation]&timeout=[timeout]

Where: isolation can be set to thread or semaphore, timeout - positive integer

-Multi thread execution available via /api/v1/multi-commands/isolation=[isolation]&timeout=[timeout]&threads=[threads]&interval=[interval]

Where: threads parameter specifies number of threads attempting to execute the commands simultaneously, and interval specifies the period application waits before spawning new thread.

