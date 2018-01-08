# hystrix-commands

Simple application showing the differences between THREAD and SEMAPHORE command isolations in Hystrix.

Application provides 2 resources:

* Single thread execution available via '/api/v1/command/isolation=[isolation]&timeout=[timeout]'

  *isolation can be set to `thread` or `semaphore`, timeout - positive integer*

* Multi thread execution available via '/api/v1/multi-commands/isolation=[isolation]&timeout=[timeout]&threads=[threads]&interval=[interval]'

  *threads parameter specifies number of threads attempting to execute the commands simultaneously, and interval specifies the    period application waits before spawning new thread.*

