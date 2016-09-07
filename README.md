## Sample atom feed

This is a simplified version of es-atom-feed-pub used to show how to
package a java web app in a container. 

The initial cut is quick and dirty to show there's really nothing 
special about packaging a java application. This is not an example
of how to use spring to build a web app (needs some work).


Some gradle commands

<pre>
gradle build
gradle build buildDocker
gradle bootRun
</pre>


### Consul

For development run it in docker

<pre>
docker run -p 8500:8500 consul agent -dev -client 0.0.0.0
</pre>

Grab consul template via the download link in the [consul template project README](https://github.com/hashicorp/consul-template)

Use seedcfg.sh to seed the config for the dev center application's configuration.

Then create the env file then run the example

<pre>
./consul-template -consul localhost:8500 -template ./atom.ctmpl:./dc1env -once
docker run --env-file ./dc1env -p 5000:8080 dasmith/atom-rest-service
</pre>
