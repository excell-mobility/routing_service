package restrouting.extended_gh.test_gh;

import com.graphhopper.*;
import com.graphhopper.routing.QueryGraph;
import com.graphhopper.routing.util.*;
import com.graphhopper.storage.*;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.Helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Manal on 09.06.2016.
 */
public class GraphBuilding {
    protected static String location = "D:\\Arbeit\\workspace\\restroutinghopper\\src\\main\\resources\\graphhopper";

    private static EncodingManager encodingManager = new EncodingManager("CAR");
    private static GraphHopperStorage graph;
//    private static LocationIndex index;

    public static GraphStorage createGraph() {
//        GraphStorage graph = new GraphBuilder(encodingManager).setLocation(location).setStore(true).create();
        graph = new GraphBuilder(encodingManager).setLocation(location).setStore(true).create();
        //Make a weighted edge between two nodes
        TurnCostExtension turnCostStorage = new TurnCostExtension();
        NodeAccess nodeAccess = graph.getNodeAccess();

        nodeAccess.setNode(0, 10, 10, 0);
        nodeAccess.setNode(1, 11, 20, 1);
        nodeAccess.setNode(2, 12, 12, 0.4);
//        graph.edge(9, 10, 200, true);

        FlagEncoder encoder = new CarFlagEncoder();
        EdgeIteratorState edge0 = graph.edge(0, 1, 100, true).setFlags(5);
//        edge0.setFlags(encoder.setProperties(5, true, true) );
//        iter2.setWayGeometry(Helper.createPointList3D(1.5, 1, 0, 2, 3, 0));
        EdgeIteratorState edge1 = graph.edge(0, 2, 150, true).setFlags(2);
        EdgeIteratorState edge2 = graph.edge(1, 2, 200, false).setFlags(1);
//        iter1.setWayGeometry(Helper.createPointList3D(3.5, 4.5, 0, 5, 6, 0));

//        turnCostStorage.addTurnInfo(iter1.getEdge(), 0, iter2.getEdge(), 1337);
//        turnCostStorage.addTurnInfo(iter2.getEdge(), 0, iter1.getEdge(), 666);
//        turnCostStorage.addTurnInfo(iter1.getEdge(), 1, iter2.getEdge(), 815);
//
        edge0.setName("named street1");
        edge1.setName("named street2");
        edge2.setName("named street3");

        graph.flush();
        graph.close();
        return graph;
    }


    public static void main(String[] args) {
//    public static void main() {
//        GraphStorage gs = createGraph();

        GraphHopperAPI instance = new GraphHopper()
                .setEncodingManager(encodingManager)
                .setGraphHopperLocation(location)
//                .disableCHShortcuts();
                .setCHEnable(false);

        GraphHopper hopper = (GraphHopper) instance;
        //TODO next line comment
        hopper.setGraphHopperStorage((GraphHopperStorage) createGraph()); // protected because only for testing?
//        hopper.load(location);

//        index = new LocationIndexTree(graph.getBaseGraph(), new RAMDirectory(location, true));
//        if (!index.loadExisting())
//            throw new IllegalStateException("location index cannot be loaded!");

        System.out.println("getNodes: "+hopper.getGraphHopperStorage().getNodes());
        System.out.println("getAllEdges: "+hopper.getGraphHopperStorage().getAllEdges());
        AllEdgesIterator iter = hopper.getGraphHopperStorage().getAllEdges();
        while(iter.next()){
            System.out.println("edge name: "+ iter.getName()+", getMaxId: "+iter.getMaxId()+", getEdge: "+iter.getEdge());
            System.out.println("edge getDistance: "+iter.getDistance()+", getFlags: "+iter.getFlags()+"\n");

        }

//         Load index
//        LocationIndex index = new LocationIndexTree(graph.getBaseGraph(), new RAMDirectory("graphhopper_folder", true));
//        if (!index.loadExisting())
//            throw new IllegalStateException("location index cannot be loaded!");

// calculate path is identical
//        QueryResult fromQR = index.findClosest(latitudeFrom, longituteFrom, EdgeFilter.ALL_EDGES);
//        QueryResult toQR = index.findClosest(latitudeTo, longituteTo, EdgeFilter.ALL_EDGES);
//        QueryGraph queryGraph = new QueryGraph(graph);
//        queryGraph.lookup(fromQR, toQR);
    }
}
