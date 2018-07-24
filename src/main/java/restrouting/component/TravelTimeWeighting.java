package restrouting.component;

import java.util.Map;

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.util.HintsMap;
import com.graphhopper.routing.weighting.AbstractWeighting;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PMap;
import com.graphhopper.util.Parameters.Routing;

import restrouting.connector.TravelTimesWrapper;

public class TravelTimeWeighting extends AbstractWeighting {
	
    protected final static double SPEED_CONV = 3.6;
    private final double headingPenalty;
    private final long headingPenaltyMillis;
    private final double maxSpeed;
    
    public TravelTimeWeighting(FlagEncoder encoder, PMap map) {
        super(encoder);
        headingPenalty = map.getDouble(Routing.HEADING_PENALTY, Routing.DEFAULT_HEADING_PENALTY);
        headingPenaltyMillis = Math.round(headingPenalty * 1000);
        maxSpeed = encoder.getMaxSpeed() / SPEED_CONV;
    }

	protected TravelTimeWeighting(FlagEncoder encoder) {
		 this(encoder, new HintsMap(0));
	}

	@Override
	public double getMinWeight(double distance) {
		return distance / maxSpeed;
	}

	@Override
	public double calcWeight(EdgeIteratorState edgeState, boolean reverse,
			int prevOrNextEdgeId) {
		
		Map<Integer, Integer> travelTimesForDetectorMap = 
				TravelTimesWrapper.getTravelTimesForDetectorMap();
		Map<Integer, Integer> travelTimesForDetectorReverseMap = 
				TravelTimesWrapper.getTravelTimesForDetectorReverseMap();
		Map<Integer, Map<Integer, Integer>> travelTimesRelationMap = 
				TravelTimesWrapper.getTravelTimesRelationMap();
		int edgeId = edgeState.getEdge();
		
		// case 1: normal edge to edge relation
		if(travelTimesRelationMap.containsKey(edgeId) 
				&& travelTimesRelationMap.get(edgeId).containsKey(prevOrNextEdgeId)) {
			return travelTimesRelationMap.get(edgeId).get(prevOrNextEdgeId) * 1.0;
		}
		
		// case 2: detector edge and not reverse
		if(travelTimesForDetectorMap.containsKey(edgeId) && !reverse) {
			return travelTimesForDetectorMap.get(edgeId) * 1.0;
		}
		
		// case 3: detector edge and reverse
		if(travelTimesForDetectorReverseMap.containsKey(edgeId) && reverse) {
			return travelTimesForDetectorReverseMap.get(edgeId) * 1.0;
		}
		
		// case 4: else calculate the travel time from osm data
        double speed = reverse ? flagEncoder.getReverseSpeed(edgeState.getFlags()) 
        		: flagEncoder.getSpeed(edgeState.getFlags());
        if (speed == 0)
            return Double.POSITIVE_INFINITY;

        double time = edgeState.getDistance() / speed * SPEED_CONV;

        // add direction penalties at start/stop/via points
        boolean unfavoredEdge = edgeState.getBool(EdgeIteratorState.K_UNFAVORED_EDGE, false);
        if (unfavoredEdge)
            time += headingPenalty;
        
        return time;
        
	}

	@Override
	public String getName() {
		return "fastest";
	}

}
