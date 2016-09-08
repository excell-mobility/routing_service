package restrouting.sensorflag;

import com.graphhopper.GraphHopper;
import com.graphhopper.matching.*;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.util.GPXEntry;

import java.util.List;

/**
 * Created by Manal on 08.09.2016.
 */
public class VAMOS_MapMatching {

    public static void main( String[] strs ) throws Exception{

        String OSM = "./src/main/resources/Dresden.osm.pbf";
        String GHLOCATION = "./src/main/resources/vamos-gh";
        String VAMOS_GPX = "./src/main/resources/vamos_gpx/vamos_roads.gpx";
        mapMatching(OSM, GHLOCATION, VAMOS_GPX);
    }



    public static void mapMatching(String OSM, String GHLOCATION, String VAMOS_GPX) {
        // import OpenStreetMap data
        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(OSM);
        hopper.setGraphHopperLocation(GHLOCATION);
        CarFlagEncoder encoder = new CarFlagEncoder();
        hopper.setEncodingManager(new EncodingManager(encoder));
        hopper.getCHFactoryDecorator().setEnabled(false);
        hopper.importOrLoad();

        // create MapMatching object, can and should be shared across threads
        GraphHopperStorage graph = hopper.getGraphHopperStorage();
        LocationIndexMatch locationIndex = new LocationIndexMatch(graph, (LocationIndexTree) hopper.getLocationIndex());
        MapMatching mapMatching = new MapMatching(graph, locationIndex, encoder);

        // do the actual matching, get the GPX entries from a file or via stream
        List<GPXEntry> inputGPXEntries = new GPXFile().doImport(VAMOS_GPX).getEntries();
        MatchResult mr = mapMatching.doWork(inputGPXEntries);

        // return GraphHopper edges with all associated GPX entries
        List<EdgeMatch> matches = mr.getEdgeMatches();
        // now do something with the edges like storing the edgeIds or doing fetchWayGeometry etc
        //TODO
        System.out.println("matches.get(0): "+matches.get(0).getEdgeState());
    }

}
