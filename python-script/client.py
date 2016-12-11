#!/usr/bin/python
import requests
import sys

base_url = 'http://localhost:8080/api/v1/'
def main(iterations, isolation, timeout):
    for i in range(0, iterations):
        resp = requests.get('%scommand?isolation=%s&timeout=%s'%(base_url, isolation, timeout))
        print 'Status %d'%resp.status_code
        print resp.text

iterations = 10
isolation = 'thread'
timeout = 500

try:
    iterations = int(sys.argv[1])
    isolation = sys.argv[2]
    timeout = sys.argv[3]
    if (isolation not in ['thread', 'semaphore']):
        raise Exception('Bad format')
except:
    print 'Unable to one or more arguments, taking values: iterations=%d, isolation=%s, timeout=%s'%(iterations, isolation, timeout)

main(iterations, isolation, timeout)
