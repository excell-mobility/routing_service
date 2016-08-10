package restrouting.extended_gh.tools;

import com.graphhopper.GraphHopper;
import com.graphhopper.util.CmdArgs;

/**
 * Created by Manal on 04.08.2016.
 */
public class Import
{
    public static void main( String[] strs ) throws Exception{

        String CONFIG="config=./src/main/resources/config.properties";
        String LOCATION="graph.location=./src/main/resources/vamos-gh";
        String OSM="osmreader.osm=./src/main/resources/Dresden.osm.pbf";
        importMautGH(CONFIG, LOCATION, OSM);
    }

    public static void importMautGH(String CONFIG, String LOCATION, String OSM){

        //## Ausgabe : java -Xmx1000m -Xms1000m -server -cp /home/imposm/mautrouting/targe                                                                                        t/maut-0.1.0.jar tools.Import config=config.properties        graph.location=/ho
        //me/imposm/data/brandenburg-latest.osm-gh osmreader.osm=/home/imposm/data/brandenburg-latest.osm.pbf
        //String LOCATION = "./target/maut-0.1.0.jar";
        String[] strsOsem={CONFIG, LOCATION, OSM};
        CmdArgs args = CmdArgs.read(strsOsem);
        GraphHopper hopper = new GraphHopper().init(args);
        hopper.importOrLoad();
        hopper.close();
    }
}
