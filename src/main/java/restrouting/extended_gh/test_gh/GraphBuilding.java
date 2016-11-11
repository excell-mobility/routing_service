

package restrouting.extended_gh.test_gh;

import com.graphhopper.*;
import com.graphhopper.routing.util.*;
import com.graphhopper.routing.weighting.FastestWeighting;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Manal on 09.06.2016.
 */
public class GraphBuilding {
    protected static String location = "./src/main/resources/graphhopperTest";

    //private static EncodingManager encodingManager = new EncodingManager("CAR");
    //private static GraphHopperStorage graph;
    private static CarFlagEncoder carEncoder = new CarFlagEncoder();
    private static EncodingManager encodingManager = new EncodingManager(carEncoder);
    private static GraphHopper hopper;
    private static GraphHopperStorage g;

    private static GraphHopperStorage createSquareGraphInstance() {

        Weighting weighting = new FastestWeighting(carEncoder);
//        GraphHopperStorage g = new GraphHopperStorage(Collections.singletonList(weighting), new RAMDirectory(), encodingManager,
//                false, new GraphExtension.NoOpExtension()).
//                create(20);
        g = new GraphBuilder(encodingManager).setLocation(location).setStore(true).create();
        //Make a weighted edge between two nodes



        //   2---3---4
        //  /    |    \
        //  1----8----5
        //  /    |    /
        //  0----7---6
        NodeAccess na = g.getNodeAccess();
        na.setNode(0, 0.000, 0.000);
        na.setNode(1, 0.001, 0.000);
        na.setNode(2, 0.002, 0.000);
        na.setNode(3, 0.002, 0.001);
        na.setNode(4, 0.002, 0.002);
        na.setNode(5, 0.001, 0.002);
        na.setNode(6, 0.000, 0.002);
        na.setNode(7, 0.000, 0.001);
        na.setNode(8, 0.001, 0.001);

        g.edge(0, 1, 100, true).setFlags(5).setName("Street1");
        g.edge(1, 2, 100, true).setFlags(1);
        g.edge(2, 3, 100, true).setFlags(1);
        g.edge(3, 4, 100, true).setFlags(5);
        g.edge(4, 5, 100, true).setFlags(1);
        g.edge(5, 6, 100, true).setFlags(5);
        g.edge(6, 7, 100, true).setFlags(1);
        g.edge(7, 0, 100, true).setFlags(5);

        g.edge(1, 8, 110, true).setFlags(1);
        g.edge(3, 8, 110, true).setFlags(5);
        g.edge(5, 8, 110, true).setFlags(1);
        g.edge(7, 8, 110, true).setFlags(5);
//        g.flush();
//        g.close();
        return g;
    }


    public static void main(String[] args) {
        GraphHopperStorage graph = createSquareGraphInstance();
        System.out.println("number of Nodes: "+graph.getNodes());
        System.out.println("Edges: "+graph.getAllEdges());

        hopper = new GraphHopper();
        hopper.setCHEnabled(true).
                setEncodingManager(encodingManager).
                setStoreOnFlush(true).
//                importOrLoad();
        setGraphHopperLocation(location);
//                setOSMFile(testOsm3);
        hopper.getCHFactoryDecorator().setWeightingsAsStrings("fastest");
        hopper.setGraphHopperStorage(graph);
        AllEdgesIterator iter = hopper.getGraphHopperStorage().getAllEdges();
        while(iter.next()){
            System.out.println("edge name: "+ iter.getName()+", getMaxId: "+iter.getMaxId()+", getEdge: "+iter.getEdge());
            System.out.println("edge getDistance: "+iter.getDistance()+", getFlags: "+iter.getFlags()+"\n");

        }
        graph.flush();
        graph.close();

    }
}

