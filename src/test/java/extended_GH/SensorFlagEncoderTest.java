package extended_GH;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import org.junit.Test;
import restrouting.sensorflag.SensorFlagEncoder;

/**
 * Created by Manal on 05.08.2016.
 */
public class SensorFlagEncoderTest {

    private String osmFile = "./src/main/resources/Dresden.osm.pbf";
    private String ghLocation = "./src/main/resources/vamosTest-gh";
    private SensorFlagEncoder sensorEncoder;
    private GraphHopper hopper;

    public SensorFlagEncoderTest() {
        hopper = new GraphHopper();
        hopper.setOSMFile(osmFile);
        hopper.setGraphHopperLocation(ghLocation);
        sensorEncoder = new SensorFlagEncoder();
        EncodingManager em = new EncodingManager(sensorEncoder);
        hopper.setEncodingManager(em);
        hopper.importOrLoad();
    }

    @Test
    public void testSensorFlagEncoder() {
        System.out.println("testSensorFlagEncoder: "+sensorEncoder.toString());
    }
}
