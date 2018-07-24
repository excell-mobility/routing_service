package restrouting.connector;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

public class TravelTimesWrapper implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Map<Integer, Map<Integer,Integer>> travelTimesRelationMap;
	private static Map<Integer, Integer> travelTimesForDetectorMap;
	private static Map<Integer, Integer> travelTimesForDetectorReverseMap;
	private static long timestamp = 0l;
	
	static {
		
		travelTimesRelationMap = Maps.newHashMap();
		travelTimesForDetectorMap = Maps.newHashMap();
		travelTimesForDetectorReverseMap = Maps.newHashMap();
		
	}
	
	public static long getTimestamp() {
		return timestamp;
	}
	
	public static void updateTimestamp() {
		TravelTimesWrapper.timestamp = System.currentTimeMillis();
	}

	public static Map<Integer, Map<Integer, Integer>> getTravelTimesRelationMap() {
		return travelTimesRelationMap;
	}

	public static void setTravelTimesRelationMap(
			Map<Integer, Map<Integer, Integer>> travelTimesRelationMap) {
		TravelTimesWrapper.travelTimesRelationMap = travelTimesRelationMap;
	}

	public static Map<Integer, Integer> getTravelTimesForDetectorMap() {
		return travelTimesForDetectorMap;
	}

	public static void setTravelTimesForDetectorMap(
			Map<Integer, Integer> travelTimesForDetectorMap) {
		TravelTimesWrapper.travelTimesForDetectorMap = travelTimesForDetectorMap;
	}

	public static Map<Integer, Integer> getTravelTimesForDetectorReverseMap() {
		return travelTimesForDetectorReverseMap;
	}

	public static void setTravelTimesForDetectorReverseMap(
			Map<Integer, Integer> travelTimesForDetectorReverseMap) {
		TravelTimesWrapper.travelTimesForDetectorReverseMap = travelTimesForDetectorReverseMap;
	}

}
