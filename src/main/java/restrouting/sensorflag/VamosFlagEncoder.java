package restrouting.sensorflag;


import com.graphhopper.reader.ReaderRelation;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.util.AbstractFlagEncoder;
import com.graphhopper.routing.util.EncodedDoubleValue;
import com.graphhopper.util.Helper;
import com.graphhopper.util.PMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

/**
 * Created by Manal on 9.09.2016.
 */
public class VamosFlagEncoder extends AbstractFlagEncoder {
    protected final Map<String, Integer> trackTypeSpeedMap;
    protected final Set<String> badSurfaceSpeedMap;
    protected int badSurfaceSpeed;
    protected final Map<String, Integer> defaultSpeedMap;

    public VamosFlagEncoder() {
        this(5, 5.0D, 0);
    }

    public VamosFlagEncoder(PMap properties) {
        this((int)properties.getLong("speed_bits", 5L), properties.getDouble("speed_factor", 5.0D), properties.getBool("turn_costs", false)?1:0);
        this.properties = properties;
        this.setBlockFords(properties.getBool("block_fords", true));
        this.setBlockByDefault(properties.getBool("block_barriers", true));
    }

    public VamosFlagEncoder(String propertiesStr) {
        this(new PMap(propertiesStr));
    }

    public VamosFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts) {
        super(speedBits, speedFactor, maxTurnCosts);
        this.trackTypeSpeedMap = new HashMap();
        this.badSurfaceSpeedMap = new HashSet();
        this.defaultSpeedMap = new HashMap();
        this.restrictions.addAll(Arrays.asList(new String[]{"motorcar", "motor_vehicle", "vehicle", "access"}));
        this.restrictedValues.add("private");
        this.restrictedValues.add("agricultural");
        this.restrictedValues.add("forestry");
        this.restrictedValues.add("no");
        this.restrictedValues.add("restricted");
        this.restrictedValues.add("delivery");
        this.restrictedValues.add("military");
        this.restrictedValues.add("emergency");
        this.intendedValues.add("yes");
        this.intendedValues.add("permissive");
        this.potentialBarriers.add("gate");
        this.potentialBarriers.add("lift_gate");
        this.potentialBarriers.add("kissing_gate");
        this.potentialBarriers.add("swing_gate");
        this.absoluteBarriers.add("bollard");
        this.absoluteBarriers.add("stile");
        this.absoluteBarriers.add("turnstile");
        this.absoluteBarriers.add("cycle_barrier");
        this.absoluteBarriers.add("motorcycle_barrier");
        this.absoluteBarriers.add("block");
        this.absoluteBarriers.add("bus_trap");
        this.absoluteBarriers.add("sump_buster");
        this.trackTypeSpeedMap.put("grade1", Integer.valueOf(20));
        this.trackTypeSpeedMap.put("grade2", Integer.valueOf(15));
        this.trackTypeSpeedMap.put("grade3", Integer.valueOf(10));
        this.badSurfaceSpeedMap.add("cobblestone");
        this.badSurfaceSpeedMap.add("grass_paver");
        this.badSurfaceSpeedMap.add("gravel");
        this.badSurfaceSpeedMap.add("sand");
        this.badSurfaceSpeedMap.add("paving_stones");
        this.badSurfaceSpeedMap.add("dirt");
        this.badSurfaceSpeedMap.add("ground");
        this.badSurfaceSpeedMap.add("grass");
        this.badSurfaceSpeedMap.add("unpaved");
        this.badSurfaceSpeedMap.add("compacted");
        this.badSurfaceSpeed = 30;
        this.maxPossibleSpeed = 140;
        this.defaultSpeedMap.put("motorway", Integer.valueOf(100));
        this.defaultSpeedMap.put("motorway_link", Integer.valueOf(70));
        this.defaultSpeedMap.put("motorroad", Integer.valueOf(90));
        this.defaultSpeedMap.put("trunk", Integer.valueOf(70));
        this.defaultSpeedMap.put("trunk_link", Integer.valueOf(65));
        this.defaultSpeedMap.put("primary", Integer.valueOf(65));
        this.defaultSpeedMap.put("primary_link", Integer.valueOf(60));
        this.defaultSpeedMap.put("secondary", Integer.valueOf(60));
        this.defaultSpeedMap.put("secondary_link", Integer.valueOf(50));
        this.defaultSpeedMap.put("tertiary", Integer.valueOf(50));
        this.defaultSpeedMap.put("tertiary_link", Integer.valueOf(40));
        this.defaultSpeedMap.put("unclassified", Integer.valueOf(30));
        this.defaultSpeedMap.put("residential", Integer.valueOf(30));
        this.defaultSpeedMap.put("living_street", Integer.valueOf(5));
        this.defaultSpeedMap.put("service", Integer.valueOf(20));
        this.defaultSpeedMap.put("road", Integer.valueOf(20));
        this.defaultSpeedMap.put("track", Integer.valueOf(15));
        this.init();
    }

    public int getVersion() {
        return 1;
    }

    public int defineWayBits(int index, int shift) {
        shift = super.defineWayBits(index, shift);
        this.speedEncoder = new EncodedDoubleValue("Speed", shift, this.speedBits, this.speedFactor, (long)((Integer)this.defaultSpeedMap.get("secondary")).intValue(), this.maxPossibleSpeed);
        return shift + this.speedEncoder.getBits();
    }

    protected double getSpeed(ReaderWay way) {
        //TODO ###### get speed from Vamos-DB #####
        String highwayValue = way.getTag("highway");
        Integer speed = (Integer)this.defaultSpeedMap.get(highwayValue);
        if(speed == null) {
            throw new IllegalStateException(this.toString() + ", no speed found for: " + highwayValue + ", tags: " + way);
        } else {
            if(highwayValue.equals("track")) {
                String tt = way.getTag("tracktype");
                if(!Helper.isEmpty(tt)) {
                    Integer tInt = (Integer)this.trackTypeSpeedMap.get(tt);
                    if(tInt != null) {
                        speed = tInt;
                    }
                }
            }

            return (double)speed.intValue();
        }
    }

    public long acceptWay(ReaderWay way) {
        String highwayValue = way.getTag("highway");
        String firstValue;
        if(highwayValue == null) {
            if(way.hasTag("route", this.ferries)) {
                firstValue = way.getTag("motorcar");
                if(firstValue == null) {
                    firstValue = way.getTag("motor_vehicle");
                }

                if(firstValue == null && !way.hasTag("foot", new String[0]) && !way.hasTag("bicycle", new String[0]) || "yes".equals(firstValue)) {
                    return this.acceptBit | this.ferryBit;
                }
            }

            return 0L;
        } else {
            if("track".equals(highwayValue)) {
                firstValue = way.getTag("tracktype");
                if(firstValue != null && !firstValue.equals("grade1") && !firstValue.equals("grade2") && !firstValue.equals("grade3")) {
                    return 0L;
                }
            }

            if(!this.defaultSpeedMap.containsKey(highwayValue)) {
                return 0L;
            } else if(!way.hasTag("impassable", "yes") && !way.hasTag("status", "impassable")) {
                firstValue = way.getFirstPriorityTag(this.restrictions);
                if(!firstValue.isEmpty()) {
                    if(this.restrictedValues.contains(firstValue) && !this.getConditionalTagInspector().isRestrictedWayConditionallyPermitted(way)) {
                        return 0L;
                    }

                    if(this.intendedValues.contains(firstValue)) {
                        return this.acceptBit;
                    }
                }

                return this.isBlockFords() && ("ford".equals(highwayValue) || way.hasTag("ford", new String[0]))?0L:(this.getConditionalTagInspector().isPermittedWayConditionallyRestricted(way)?0L:this.acceptBit);
            } else {
                return 0L;
            }
        }
    }

    public long handleRelationTags(ReaderRelation relation, long oldRelationFlags) {
        return oldRelationFlags;
    }

    public long handleWayTags(ReaderWay way, long allowed, long relationFlags) {
        if(!this.isAccept(allowed)) {
            return 0L;
        } else {
            long flags = 0L;
            double ferrySpeed;
            if(!this.isFerry(allowed)) {
                ferrySpeed = this.getSpeed(way);
                ferrySpeed = this.applyMaxSpeed(way, ferrySpeed);
                ferrySpeed = this.applyBadSurfaceSpeed(way, ferrySpeed);
                flags = this.setSpeed(flags, ferrySpeed);
                boolean isRoundabout = way.hasTag("junction", "roundabout");
                if(isRoundabout) {
                    flags = this.setBool(flags, 2, true);
                }

                if(!this.isOneway(way) && !isRoundabout) {
                    flags |= this.directionBitMask;
                } else {
                    if(this.isBackwardOneway(way)) {
                        flags |= this.backwardBit;
                    }

                    if(this.isForwardOneway(way)) {
                        flags |= this.forwardBit;
                    }
                }
            } else {
                ferrySpeed = this.getFerrySpeed(way, (double)((Integer)this.defaultSpeedMap.get("living_street")).intValue(), (double)((Integer)this.defaultSpeedMap.get("service")).intValue(), (double)((Integer)this.defaultSpeedMap.get("residential")).intValue());
                flags = this.setSpeed(flags, ferrySpeed);
                flags |= this.directionBitMask;
            }

            return flags;
        }
    }

    protected boolean isBackwardOneway(ReaderWay way) {
        return way.hasTag("oneway", "-1") || way.hasTag("vehicle:forward", "no") || way.hasTag("motor_vehicle:forward", "no");
    }

    protected boolean isForwardOneway(ReaderWay way) {
        return !way.hasTag("oneway", "-1") && !way.hasTag("vehicle:forward", "no") && !way.hasTag("motor_vehicle:forward", "no");
    }

    protected boolean isOneway(ReaderWay way) {
        return way.hasTag("oneway", this.oneways) || way.hasTag("vehicle:backward", new String[0]) || way.hasTag("vehicle:forward", new String[0]) || way.hasTag("motor_vehicle:backward", new String[0]) || way.hasTag("motor_vehicle:forward", new String[0]);
    }

    public String getWayInfo(ReaderWay way) {
        String str = "";
        String highwayValue = way.getTag("highway");
        if("motorway_link".equals(highwayValue)) {
            String destination = way.getTag("destination");
            if(!Helper.isEmpty(destination)) {
                int counter = 0;
                String[] var6 = destination.split(";");
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String d = var6[var8];
                    if(!d.trim().isEmpty()) {
                        if(counter > 0) {
                            str = str + ", ";
                        }

                        str = str + d.trim();
                        ++counter;
                    }
                }
            }
        }

        return str.isEmpty()?str:(str.contains(",")?"destinations: " + str:"destination: " + str);
    }

    protected double applyBadSurfaceSpeed(ReaderWay way, double speed) {
        if(this.badSurfaceSpeed > 0 && speed > (double)this.badSurfaceSpeed && way.hasTag("surface", this.badSurfaceSpeedMap)) {
            speed = (double)this.badSurfaceSpeed;
        }

        return speed;
    }

    public String toString() {
        return "car";
    }
}
