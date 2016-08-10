package extended_GH;

import com.graphhopper.reader.OSMWay;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Manal on 26.07.2016.
 */
public class CarMaxSpeedTest {

    private final EncodingManager em = new EncodingManager("car");
    private final CarFlagEncoder encoder = (CarFlagEncoder) em.getEncoder("car");

    @Test
    public void testMaxSpeed()
    {
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "trunk");
        way.setTag("maxspeed", "500");
        long allowed = encoder.acceptWay(way);
        long encoded = encoder.handleWayTags(way, allowed, 0);

        System.out.println("encoded: "+encoded);
        System.out.println("getSpeed: "+encoder.getSpeed(encoded));
        assertEquals(140, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:backward", "10");
        way.setTag("maxspeed:forward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(10, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:forward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(20, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:backward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(20, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "motorway");
        way.setTag("maxspeed", "none");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(125, encoder.getSpeed(encoded), .1);
    }

    @Test
    @Ignore
    public void testSetSpeed()
    {
        long flags = encoder.setProperties(10, true, true);
        System.out.println("flag speed before: "+ flags);
        //encoder.speedFactor
        flags = encoder.setSpeed(flags, 5* 0.49);
        System.out.println("flag speed: "+ flags);

        assertEquals(0, encoder.getSpeed(flags), .1);
        assertEquals(0, encoder.getReverseSpeed(flags), .1);
        assertFalse(encoder.isForward(flags));
        assertFalse(encoder.isBackward(flags));
    }

}
