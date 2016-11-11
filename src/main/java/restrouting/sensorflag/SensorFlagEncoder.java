package restrouting.sensorflag;

import com.graphhopper.reader.ReaderRelation;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.util.AbstractFlagEncoder;
import com.graphhopper.routing.util.EncodedDoubleValue;
import com.graphhopper.routing.util.EncodedValue;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.Helper;
import com.graphhopper.util.PMap;
import restrouting.model.SensorData;

import java.util.*;

/**
 * Created by Manal on 04.08.2016.
 */
public class SensorFlagEncoder extends AbstractFlagEncoder {
    @Override
    public long handleRelationTags(ReaderRelation readerRelation, long l) {
        return 0;
    }

    @Override
    public long acceptWay(ReaderWay readerWay) {
        return 0;
    }

    @Override
    public long handleWayTags(ReaderWay readerWay, long l, long l1) {
        return 0;
    }

    @Override
    public int getVersion() {
        return 0;
    }
//    protected final Map<String, Integer> trackTypeSpeedMap = new HashMap<String, Integer>();
//    protected final Set<String> badSurfaceSpeedMap = new HashSet<String>();
//    private EncodedValue junctionSpeedTurnEncoder; //encoder for sensors on junction
//    private EncodedDoubleValue sensorSpeedEncoder;//encoder for other sensors
//    private final Map<String, Integer> junctionTurnDirectionsMap = new HashMap<>();
//    private final List<String> junctionTurnDirectionsList = new ArrayList<>();
//   // private final Map<String, List<Integer>> junctionSpeedDirectionsMap = new HashMap<>();
//    private long sensorFlagBit;
//    private double sensorSpeed;
//    /**
//     * A map which associates string to speed. Get some impression:
//     * http://www.itoworld.com/map/124#fullscreen
//     * http://wiki.openstreetmap.org/wiki/OSM_tags_for_routing/Maxspeed
//     */
//    protected final Map<String, Integer> defaultSpeedMap = new HashMap<String, Integer>();
//
    public SensorFlagEncoder()
    {
        this(5, 5.0D, 0);
    }
//
//    public SensorFlagEncoder( PMap properties )
//    {
//        this((int) properties.getLong("speed_bits", 5),
//                properties.getDouble("speed_factor", 5),
//                properties.getBool("turn_costs", false) ? 1 : 0);
//        this.properties = properties;
//        this.setBlockFords(properties.getBool("block_fords", true));
//    }
//
//    public SensorFlagEncoder( String propertiesStr )
//    {
//        this(new PMap(propertiesStr));
//    }
//
    public SensorFlagEncoder( int speedBits, double speedFactor, int maxTurnCosts )
    {
        super(speedBits, speedFactor, maxTurnCosts);
//        restrictions.addAll(Arrays.asList("motorcar", "motor_vehicle", "vehicle", "access"));
//        restrictedValues.add("private");
//        restrictedValues.add("agricultural");
//        restrictedValues.add("forestry");
//        restrictedValues.add("no");
//        restrictedValues.add("restricted");
//        restrictedValues.add("delivery");
//        restrictedValues.add("military");
//        restrictedValues.add("emergency");
//
//        intendedValues.add("yes");
//        intendedValues.add("permissive");
//
//        potentialBarriers.add("gate");
//        potentialBarriers.add("lift_gate");
//        potentialBarriers.add("kissing_gate");
//        potentialBarriers.add("swing_gate");
//
//        absoluteBarriers.add("bollard");
//        absoluteBarriers.add("stile");
//        absoluteBarriers.add("turnstile");
//        absoluteBarriers.add("cycle_barrier");
//        absoluteBarriers.add("motorcycle_barrier");
//        absoluteBarriers.add("block");
//        absoluteBarriers.add("bus_trap");
//        absoluteBarriers.add("sump_buster");
//
//        trackTypeSpeedMap.put("grade1", 20); // paved
//        trackTypeSpeedMap.put("grade2", 15); // now unpaved - gravel mixed with ...
//        trackTypeSpeedMap.put("grade3", 10); // ... hard and soft materials
//        trackTypeSpeedMap.put("grade4", 5); // ... some hard or compressed materials
//        trackTypeSpeedMap.put("grade5", 5); // ... no hard materials. soil/sand/grass
//
//        badSurfaceSpeedMap.add("cobblestone");
//        badSurfaceSpeedMap.add("grass_paver");
//        badSurfaceSpeedMap.add("gravel");
//        badSurfaceSpeedMap.add("sand");
//        badSurfaceSpeedMap.add("paving_stones");
//        badSurfaceSpeedMap.add("dirt");
//        badSurfaceSpeedMap.add("ground");
//        badSurfaceSpeedMap.add("grass");
//
//        maxPossibleSpeed = 140;
//
//        // autobahn
//        defaultSpeedMap.put("motorway", 100);
//        defaultSpeedMap.put("motorway_link", 70);
//        defaultSpeedMap.put("motorroad", 90);
//        // bundesstraße
//        defaultSpeedMap.put("trunk", 70);
//        defaultSpeedMap.put("trunk_link", 65);
//        // linking bigger town
//        defaultSpeedMap.put("primary", 65);
//        defaultSpeedMap.put("primary_link", 60);
//        // linking towns + villages
//        defaultSpeedMap.put("secondary", 60);
//        defaultSpeedMap.put("secondary_link", 50);
//        // streets without middle line separation
//        defaultSpeedMap.put("tertiary", 50);
//        defaultSpeedMap.put("tertiary_link", 40);
//        defaultSpeedMap.put("unclassified", 30);
//        defaultSpeedMap.put("residential", 30);
//        // spielstraße
//        defaultSpeedMap.put("living_street", 5);
//        defaultSpeedMap.put("service", 20);
//        // unknown road
//        defaultSpeedMap.put("road", 20);
//        // forestry stuff
//        defaultSpeedMap.put("track", 15);
//
//        junctionTurnDirectionsList.addAll(Arrays.asList("left", "slight_left", "sharp_left", "through", "right",
//                "slight_right", "sharp_right", "reverse", "merge_to_left", "merge_to_right", "none"));
//        int counter = 0;
//        for (String tm : junctionTurnDirectionsList){
//            junctionTurnDirectionsMap.put(tm, counter++);
//        }
//
//        //turn = left, slight_left, sharp_left, through, right,
//        // slight_right, sharp_right, reverse, merge_to_left, merge_to_right, none
////        junctionTurnDirectionsMap.put("turn:lanes:forward", "left|through;right");//lanes = 2
////        junctionTurnDirectionsMap.put("turn:lanes:forward", "|left|through|through;right");//lanes = 3
////        junctionTurnDirectionsMap.put("turn:lanes:backward", "left|through;right");
////        //oneway
////        junctionTurnDirectionsMap.put("turn:lanes", "through;right");
//
//        //speed:lanes default
////        List<Integer> speedLanes = Arrays.asList(20, 50, 30);
////        junctionSpeedDirectionsMap.put("speed:lanes", speedLanes);
//        conditionalTagsInspector = new ConditionalTagsInspector(DateRangeParser.createCalendar(), restrictions, restrictedValues, intendedValues);
    }
//
//    @Override
//    public int getVersion()
//    {
//        return 1;
//    }
//
//    /**
//     * Define the place of the speedBits in the edge flags for car.
//     */
//    @Override
//    public int defineWayBits( int index, int shift )
//    {//Bit for sensor
//        sensorFlagBit = 1L << shift;
//        shift++;
//        // first two bits are reserved for route handling in superclass
//        shift = super.defineWayBits(index, shift);
//
//        //( String name, int shift, int bits, double factor, long defaultValue, int maxValue, boolean allowZero )
//        sensorSpeedEncoder = new EncodedDoubleValue("SensorSpeed", shift, speedBits, speedFactor, 0, maxPossibleSpeed, true);//maxPossibleSpeed =140
//        shift += sensorSpeedEncoder.getBits();
//
//
////        junctionSpeedTurnEncoder = new EncodedDoubleValue("speed:lanes", shift, speedBits, speedFactor, 0, junctionSpeedDirectionsMap.size(), true);
//        junctionSpeedTurnEncoder = new EncodedDoubleValue("turn:lanes", shift, speedBits, speedFactor, 0, junctionTurnDirectionsMap.size(), true);
//        shift += junctionSpeedTurnEncoder.getBits();
//
//        speedEncoder = new EncodedDoubleValue("Speed", shift, speedBits, speedFactor, defaultSpeedMap.get("secondary"),
//                maxPossibleSpeed);
//        shift += speedEncoder.getBits();
//        return shift;
//    }
//
//    protected double getSensorSpeed(OSMWay way , SensorData sensor) {
//        double speed = 0.0;
//        //TODO with GPS coord.
//        if(way.getNodes().contains(sensor.getId()) && getSpeed(way)< sensor.getSpeed()) {
//            this.sensorSpeed = sensor.getSpeed();
//            speed = this.sensorSpeed;
//        }
//        return speed;
//    }
//
//
//    protected double getSpeed( OSMWay way ) {
//        String highwayValue = way.getTag("highway");
//        Integer speed = defaultSpeedMap.get(highwayValue);
//        if (speed == null)
//            throw new IllegalStateException(toString() + ", no speed found for: " + highwayValue + ", tags: " + way);
//
//        if (highwayValue.equals("track"))
//        {
//            String tt = way.getTag("tracktype");
//            if (!Helper.isEmpty(tt))
//            {
//                Integer tInt = trackTypeSpeedMap.get(tt);
//                if (tInt != null)
//                    speed = tInt;
//            }
//        }
//        return speed;
//    }
//
//    @Override
//    public long acceptWay( OSMWay way )
//    {
//        // TODO: Ferries have conditionals, like opening hours or are closed during some time in the year
//        String highwayValue = way.getTag("highway");
//        if (highwayValue == null)
//        {
//            if (way.hasTag("route", ferries))
//            {
//                String motorcarTag = way.getTag("motorcar");
//                if (motorcarTag == null)
//                    motorcarTag = way.getTag("motor_vehicle");
//
//                if (motorcarTag == null && !way.hasTag("foot") && !way.hasTag("bicycle") || "yes".equals(motorcarTag))
//                    return acceptBit | ferryBit;
//            }
//            return 0;
//        }
//
//        if ("track".equals(highwayValue))
//        {
//            String tt = way.getTag("tracktype");
//            if (tt != null && !tt.equals("grade1") && !tt.equals("grade2") && !tt.equals("grade3"))
//                return 0;
//        }
//
//        if (!defaultSpeedMap.containsKey(highwayValue))
//            return 0;
//
//        if (way.hasTag("impassable", "yes") || way.hasTag("status", "impassable"))
//            return 0;
//
//        // multiple restrictions needs special handling compared to foot and bike, see also motorcycle
//        String firstValue = way.getFirstPriorityTag(restrictions);
//        if (!firstValue.isEmpty())
//        {
//            if (restrictedValues.contains(firstValue) && !conditionalTagsInspector.isRestrictedWayConditionallyPermitted(way))
//                return 0;
//            if (intendedValues.contains(firstValue))
//                return acceptBit;
//        }
//
//        // do not drive street cars into fords
//        if (isBlockFords() && ("ford".equals(highwayValue) || way.hasTag("ford")))
//            return 0;
//
//        if (conditionalTagsInspector.isPermittedWayConditionallyRestricted(way))
//            return 0;
//        else
//            return acceptBit;
//    }
//
//    @Override
//    public long handleRelationTags(OSMRelation relation, long oldRelationFlags )
//    {
//        return oldRelationFlags;
//    }
//
//    public long handleSensorTags( OSMWay way, SensorData sensor, long allowed, long relationFlags )
//    {
//        return 0L;
//
//    }
//    @Override
//    public long handleWayTags( OSMWay way, long allowed, long relationFlags )
//    {
//        if (!isAccept(allowed))
//            return 0;
//
//        long flags = 0;
//        if (!isFerry(allowed))
//        {
//            // get assumed speed from highway type
//            double speed = getSpeed(way);
//            speed = applyMaxSpeed(way, speed);
//
////            this.sensorSpeed = parseSpeed(way.getTag("speed"));
//            if (speed < this.sensorSpeed)
//                speed = this.sensorSpeed;
//
//            // limit speed to max 30 km/h if bad surface
//            if (speed > 30 && way.hasTag("surface", badSurfaceSpeedMap))
//                speed = 30;
//
//            if (sensorSpeed > 0)
//                flags = sensorSpeedEncoder.setDoubleValue(flags, sensorSpeed);
//
//            flags = setSpeed(flags, speed);
//
//            boolean isRoundabout = way.hasTag("junction", "roundabout");
//            if (isRoundabout)
//                flags = setBool(flags, K_ROUNDABOUT, true);
//
//            if (isOneway(way) || isRoundabout)
//            {
//                if (isBackwardOneway(way))
//                    flags |= backwardBit;
//
//                if (isForwardOneway(way))
//                    flags |= forwardBit;
//            } else
//                flags |= directionBitMask;
//
//        } else {
//            double ferrySpeed = getFerrySpeed(way, defaultSpeedMap.get("living_street"), defaultSpeedMap.get("service"), defaultSpeedMap.get("residential"));
//            flags = setSpeed(flags, ferrySpeed);
//            flags |= directionBitMask;
//        }
//        String turnDirectionValue = way.getTag("surface");
//        Integer sValue = junctionTurnDirectionsMap.get(turnDirectionValue);
//        if (sValue == null)
//            sValue = 0;
//        flags = this.junctionSpeedTurnEncoder.setValue(flags, sValue);
//
//        if (!isSensorFlagEmpty(flags))
//            throw new IllegalStateException("SensorFlag bit has to be empty on creation");
//
//        return flags;
//    }
//
//    public int getJunctionTurn( EdgeIteratorState edge ) {
//        return (int) junctionSpeedTurnEncoder.getValue(edge.getFlags());
//    }
//
//    public String getJunctionTurnDirectionsAsString( EdgeIteratorState edge )
//    {
//        int val = getJunctionTurn(edge);
//        for (Map.Entry<String, Integer> e : junctionTurnDirectionsMap.entrySet())
//        {
//            if (e.getValue() == val)
//                return e.getKey();
//        }
//        return null;
//    }
//
//    private boolean isSensorFlagEmpty( long flags )
//    {
//        return (flags & sensorFlagBit) == 0;
//    }
//
//    /**
//     * make sure that isOneway is called before
//     */
//    protected boolean isBackwardOneway( OSMWay way )
//    {
//        return way.hasTag("oneway", "-1")
//                || way.hasTag("vehicle:forward", "no")
//                || way.hasTag("motor_vehicle:forward", "no");
//    }
//
//    /**
//     * make sure that isOneway is called before
//     */
//    protected boolean isForwardOneway( OSMWay way )
//    {
//        return !way.hasTag("oneway", "-1")
//                && !way.hasTag("vehicle:forward", "no")
//                && !way.hasTag("motor_vehicle:forward", "no");
//    }
//
//    protected boolean isOneway( OSMWay way )
//    {
//        return way.hasTag("oneway", oneways)
//                || way.hasTag("vehicle:backward")
//                || way.hasTag("vehicle:forward")
//                || way.hasTag("motor_vehicle:backward")
//                || way.hasTag("motor_vehicle:forward");
//    }
//
//    public String getWayInfo( OSMWay way )
//    {
//        String str = "";
//        String highwayValue = way.getTag("highway");
//        // for now only motorway links
//        if ("motorway_link".equals(highwayValue))
//        {
//            String destination = way.getTag("destination");
//            if (!Helper.isEmpty(destination))
//            {
//                int counter = 0;
//                for (String d : destination.split(";"))
//                {
//                    if (d.trim().isEmpty())
//                        continue;
//
//                    if (counter > 0)
//                        str += ", ";
//
//                    str += d.trim();
//                    counter++;
//                }
//            }
//        }
//        if (str.isEmpty())
//            return str;
//        // I18N
//        if (str.contains(","))
//            return "destinations: " + str;
//        else
//            return "destination: " + str;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "sensor_car";
//    }
}
