# ExCELL Routing Service

This routing service requires a latitude and longitude input for two locations to calculate the shortest route in between. The routing is based on the Dijkstra algorithm. The service acts as a wrapper around the well-known and powerful [OpenStreetMap](https://www.openstreetmap.org/) routing engine [Graphhopper](https://www.graphhopper.com/).

## Run the service

This web service comes as a [SpringBoot](https://projects.spring.io/spring-boot/) application so it's very easy to test it on your local machine. If you run the service from inside a Java IDE a Tomcat server will be launched and you can access the service through a browser via localhost:43434.

### Application properties

As the service uses the Graphhopper routing engine it requires an OSM file to produce the routing graph. Download any OSM pbf file you want e.g. from [GeoFabrik](http://download.geofabrik.de/) and reference the file location in the application.properties (4th parameter) file of this project. You also need to specify a directory where Graphhopper shall create the routing graph. By default it's src/main/resources/graphhopper.

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
