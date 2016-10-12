package restrouting.extended_gh.mapmatching;

import com.graphhopper.GraphHopper;

//import com.graphhopper.matching.EdgeMatch;
//import com.graphhopper.matching.MapMatching;
import com.graphhopper.matching.LocationIndexMatch;
import com.graphhopper.matching.MatchResult;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.GPXEntry;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Manal on 08.09.2016.
 */
public class VAMOS_MapMatching {

    private static final String RELATIVE_TARGET_DIRECTORY = "./data-converted/csv";

    public static void main(String[] strs) throws Exception {

//        String OSM = "./src/main/resources/Dresden.osm.pbf";
        String OSM = "./src/main/resources/sachsen-latest.osm.pbf";
        String GHLOCATION = "./src/main/resources/vamos-gh";
        String VAMOS_GPXCSV = RELATIVE_TARGET_DIRECTORY+"/nodes_sections_geopointsBig.csv";

        // gh example
//        String OSM = "./src/main/resources/leipzig_germany.osm.pbf";
//        String VAMOS_GPX = "./src/main/resources/vamos_gpx/test1.gpx";
        mapMatching(OSM, GHLOCATION, VAMOS_GPXCSV);
    }

    public static List<EdgeMatch> mapMatching(String OSM, String GHLOCATION, String VAMOS_GPXCSV) {
        File ghDirectory  = new File(GHLOCATION);
        //delete GH
        if(ghDirectory.exists() && ghDirectory.isDirectory()){
            try {
                FileUtils.deleteDirectory(ghDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
//        List<GPXEntry> inputGPXEntries = new GPXFile().doImport(VAMOS_GPX).getEntries();
        List<GPXEntry> inputGPXEntries = scannCSVFile(new File(VAMOS_GPXCSV), 9, 10, 11);
        System.out.println("inputGPXEntries length before : "+ inputGPXEntries.size());

        //remove duplicates
        List<GPXEntry> inputGPXTemp = removeDuplicates(inputGPXEntries);
        System.out.println("inputGPXEntries length after removing duplicates : "+ inputGPXTemp.size());
        inputGPXEntries = inputGPXTemp;

        //write gpxEntries in a file
        int counter = 0;
        BufferedWriter bw = null;
        String newGPX = RELATIVE_TARGET_DIRECTORY +"/vamosGPX.csv";

        try {
            bw = new BufferedWriter(new FileWriter(newGPX));
        for(GPXEntry gpx : inputGPXEntries){
            String line = counter++ + " , " +gpx.toString();
            bw.write(line);
            bw.newLine();
        }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // accuracy works only with version 0.8-SNAPSHOT
        int gpsAccuracy = 30;//40 meter accuracy
        //mapMatching.setMeasurementErrorSigma(gpsAccuracy);
//        mapMatching.setMaxVisitedNodes((int) Math.pow(10,10));
//        mapMatching.setMaxVisitedNodes(2147483647);
        restrouting.extended_gh.mapmatching.MatchResult mr = mapMatching.doWork(inputGPXEntries);

        // return GraphHopper edges with all associated GPX entries
        List<EdgeMatch> matches = mr.getEdgeMatches();
        System.out.println("List<EdgeMatch> matches length: "+ matches.size());

        //write EdgeMatches in a file
        int ct = 0;
        BufferedWriter bwMatches = null;
        String edgeMatches = RELATIVE_TARGET_DIRECTORY + "/vamosMatches.csv";

        try {
            bw = new BufferedWriter(new FileWriter(edgeMatches));
            for(EdgeMatch edgeMatch : matches){
                String line = ct++ + " , " + edgeMatch.getEdgeState().getName() + " , " + edgeMatch.toString();
//                System.out.println("edgeMatch: "+ edgeMatch.getEdgeState());

                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // now do something with the edges like storing the edgeIds or doing fetchWayGeometry etc
        System.out.println("matches.get(0): "+matches.get(0).getEdgeState());
        System.out.println("matches.get(0).getGpxExtensions: "+matches.get(0).getGpxExtensions());
        return matches;
    }

    /**
     * return GPXEntries from vamos data as list
     * @param file
     * @param latPart
     * @param lonPart
     * @param time
     * @return
     */
    public static List<GPXEntry> scannCSVFile(File file, int latPart, int lonPart, int time ) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // Skip first line.
            String line;
            int counter = 0;
            List<GPXEntry> scanner = new ArrayList<GPXEntry>();

            //get timestamp in millis
            final String DATE_FORMAT_MS = "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat formatterZMS = new SimpleDateFormat(DATE_FORMAT_MS);

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                float lat = Float.parseFloat(parts[latPart]);
                float lon = Float.parseFloat(parts[lonPart]);
                String timestamp = parts[time];
                long millis = formatterZMS.parse(timestamp).getTime();
                scanner.add(new GPXEntry(lat, lon, 0, millis));
        }
            return scanner;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<GPXEntry> removeDuplicates(List<GPXEntry> list){
        int count = list.size();
        for (int i = 0; i < count; i++)  {
            for (int j = i + 1; j < count; j++) {
                if (list.get(i).getLat() == list.get(j).getLat() && list.get(i).getLon() == list.get(j).getLon()) {
                    list.remove(j--);
                    count--;
                }
            }
        }
        return list;
    }

    private static List<EdgeMatch> removeMatchDuplicates(List<EdgeMatch> list){
        int count = list.size();
        for (int i = 0; i < count; i++)  {
            for (int j = i + 1; j < count; j++) {
                if (list.get(i).getEdgeState() == list.get(j).getEdgeState()) {
                    list.remove(j--);
                    count--;
                }
            }
        }
        return list;
    }

}
