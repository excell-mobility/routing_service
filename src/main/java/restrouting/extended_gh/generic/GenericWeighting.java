/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package restrouting.extended_gh.generic;

//import com.graphhopper.util.EdgeIteratorState;
//import com.graphhopper.routing.util.AbstractWeighting;
import restrouting.extended_gh.util.EdgeIteratorState;
import com.graphhopper.util.Parameters;
import restrouting.extended_gh.util.ConfigMap;


/**
 * Calculates the best route according to a configurable weighting.
 * <p>
 *
 * @author Peter Karich
 */
public class GenericWeighting extends AbstractWeighting
{
    /**
     * Converting to seconds is not necessary but makes adding other penalties easier (e.g. turn
     * costs or traffic light costs etc)
     */
    protected final static double SPEED_CONV = 3.6;
    private final double headingPenalty;
    private final double maxSpeed;
    private final GenericFlagEncoder gEncoder;
    private final double[] speedArray;
    private final int accessType;

    public GenericWeighting( GenericFlagEncoder encoder, ConfigMap cMap )
    {
        super(encoder);
        gEncoder = encoder;
        headingPenalty = cMap.getDouble(Parameters.Routing.HEADING_PENALTY, Parameters.Routing.DEFAULT_HEADING_PENALTY);
        maxSpeed = cMap.getDouble("maxspeed", encoder.getMaxPossibleSpeed()) / SPEED_CONV;

        speedArray = gEncoder.getHighwaySpeedMap(cMap.getMap("highways", Double.class));
        accessType = gEncoder.getAccessType("motor_vehicle");
    }

    @Override
    public double getMinWeight( double distance )
    {
        return distance / maxSpeed;
    }


    @Override
    public double calcWeight(EdgeIteratorState edge, boolean reverse, int prevOrNextEdgeId )
    {
        // handle oneways and removed edges via subnetwork removal (existing and allowed highway tags but 'island' edges)
        if (reverse)
        {
            if (!gEncoder.isBackward(edge, accessType))
                return Double.POSITIVE_INFINITY;
        } else
        if (!gEncoder.isForward(edge, accessType))
            return Double.POSITIVE_INFINITY;

        // TODO to avoid expensive reverse flags include oneway accessibility
        // but how to include e.g. maxspeed as it depends on direction? Does highway depend on direction?
        // reverse = edge.isReverse()? !reverse : reverse;
        int highwayVal = gEncoder.getHighway(edge);
        double speed = speedArray[highwayVal];
        if (speed < 0)
            throw new IllegalStateException("speed was negative? " + edge.getEdge() + ", highway:" + highwayVal + ", reverse:" + reverse);
        if (speed == 0)
            return Double.POSITIVE_INFINITY;

        // TODO inner city guessing -> lit, maxspeed <= 50, residential etc => create new encoder.isInnerCity(edge)
        // See #472 use edge.getDouble((encoder), K_MAXSPEED_MOTORVEHICLE_FORWARD, _default) or edge.getMaxSpeed(...) instead?
        // encoder could be made optional via passing to EdgeExplorer
        double maxspeed = gEncoder.getMaxspeed(edge, accessType, reverse);
        if (maxspeed > 0 && speed > maxspeed)
            speed = maxspeed;

        double time = edge.getDistance() / speed * SPEED_CONV;

        // add direction penalties at start/stop/via points
        boolean unfavoredEdge = edge.getBool(EdgeIteratorState.K_UNFAVORED_EDGE, false);
        if (unfavoredEdge)
            time += headingPenalty;

        if (time < 0)
            throw new IllegalStateException("Some problem with weight calculation: time:" + time + ", speed:" + speed);

        // TODO avoid a certain (or multiple) bounding boxes (less efficient for just a few edges) or a list of edgeIDs (not good for large areas)
        // bbox.contains(nodeAccess.getLatitude(edge.getBaseNode()), nodeAccess.getLongitude(edge.getBaseNode())) time+=avoidPenalty;
        return time;
    }

    @Override
    public String getName()
    {
        return "generic";
    }
}
