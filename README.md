# ExCELL Routing Service

This routing service requires a latitude and longitude input for two locations to calculate the shortest route in between. The routing is based on the Dijkstra algorithm. The service acts as a wrapper around the well-known and powerful [OpenStreetMap](https://www.openstreetmap.org/) routing engine [Graphhopper](https://www.graphhopper.com/). ExCELL routing service was mainly developed at Beuth Hochschule für Technik Berlin (BHS) by Stephan Pieper and Felix Kunde.


## Setup

This web service comes as a [SpringBoot](https://projects.spring.io/spring-boot/) application so it's very easy to test it on your local machine. If you run the service from inside a Java IDE a Tomcat server will be launched and you can access the service through a browser via localhost:43434.

### Application properties

As the service uses the Graphhopper routing engine it requires an OSM file to produce the routing graph. Download any OSM pbf file you want e.g. from [GeoFabrik](http://download.geofabrik.de/) and reference the file location in the application.properties (4th parameter) file of this project. You also need to specify a directory where Graphhopper shall create the routing graph. By default it's src/main/resources/graphhopper.

To enable routing on behalf of real time travel times switch the parameter `routing.fetchTravelTimes` to `true`. Now the ExCELL travel time API will be called. Make sure that the RoutingService and the TravelTimesService are operating on the same Graphhopper graph. Currently, there no on-the-fly edge matching is implemented, in case different graphs are used. The user can set up his own TravelTimeService or use the online API of the ExCELL platform, which is the default setting but requires a user login. For the online version a user token is provided by the [ExCELL API Gateway](https://dlr-integration.minglabs.com/api/v1/tokenauth/). If user authentification is not required change the parameter of `url.traveltimeservice.auth` to false.

### Build it

The project is using [Maven](https://maven.apache.org/) as a build tool and for managing the software dependencies. So in order to build the software you should install Maven on your machine. To create an executable JAR file for your local machine open you favourite shell environment and run:

<pre>mvn clean package</pre>

This creates a JAR file called `routing-0.1.0.jar`. You can change the name in the pom.xml file. If you plan to run the service on a remote machine, it is necessary to include the dependencies within the JAR file. To create a fat or "shaded" JAR use:

<pre>mvn clean package shade:shade</pre>

### Run it

On your local machine run the JAR with:

<pre>java -jar routing-0.1.0.jar</pre>

On a remote machine use it is necessary to specify the location of the OSM file and routing graph directory. You might also want to change the server port

<pre>java -jar routing-0.1.0.jar --server.port=44444 --routing.ghlocation=/path/to/routing/graph --routing.osmfile=/path/to/osm/file.osm.pbf</pre>

I you get an error that the main class is unknow user `-cp` with the path to the main class instead of the `-jar` operator.

<pre>java -cp restrouting/Application.java routing-0.1.0.jar --server.port=44444 --routing.ghlocation=/path/to/routing/graph --routing.osmfile=/path/to/osm/file.osm.pbf</pre>


## API Doc

This projects provides a [Swagger](https://swagger.io/) interface to support the Open API initiative. The Java library [Springfox](http://springfox.github.io/springfox/) is used to automatically create the swagger UI configuration from annotations in the Java Spring code.

An online version of the routing API is available on the ExCELL Developer Portal: [Try it out!](https://www.excell-mobility.de/developer/docs.php?service=routing_service). You need to sign up first in order to access the services from the portal. Every user receives a token that he/she has to use for authorization for each service.


## Developers

* Stephan Pieper (BHS)
* Felix Kunde (BHS)
* Maximilian Allies (BHS)
* Henning Jeske (Technische Universität Dresden)


## Contact

* spieper [at] beuth-hochschule.de
* fkunde [at] beuth-hochschule.de


## Acknowledgement
The Routing Service has been realized within the ExCELL project funded by the Federal Ministry for Economic Affairs and Energy (BMWi) and German Aerospace Center (DLR) - agreement 01MD15001B.


## Special Thanks

* Graphopper Team


## Disclaimer

THIS SOFTWARE IS PROVIDED "AS IS" AND "WITH ALL FAULTS." 
BHS MAKES NO REPRESENTATIONS OR WARRANTIES OF ANY KIND CONCERNING THE 
QUALITY, SAFETY OR SUITABILITY OF THE SKRIPTS, EITHER EXPRESSED OR 
IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED WARRANTIES OF 
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT.

IN NO EVENT WILL BHS BE LIABLE FOR ANY INDIRECT, PUNITIVE, SPECIAL, 
INCIDENTAL OR CONSEQUENTIAL DAMAGES HOWEVER THEY MAY ARISE AND EVEN IF 
BHS HAS BEEN PREVIOUSLY ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
