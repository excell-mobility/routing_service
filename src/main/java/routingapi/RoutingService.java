package routingapi;

import java.util.List;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;

@Component
public class RoutingService {

	private final Logger log;
	private GraphHopper hopper;
	
	private String osmFile;
	private String ghLocation;
	
	private double distance;
	private long timeInMs;
	private PointList pointList;

	@Autowired
	public RoutingService(
			@Value("${routing.osmfile}") String osmFile,
			@Value("${routing.ghlocation}") String ghLocation) {
		
		this.osmFile = osmFile;
		this.ghLocation = ghLocation;
		
		log = LoggerFactory.getLogger(this.getClass());
		
		hopper = new GraphHopper().forServer();
		hopper.setOSMFile(this.getOsmFile());
		hopper.setGraphHopperLocation(this.getGhLocation());
		hopper.setEncodingManager(new EncodingManager("car"));
		hopper.importOrLoad();
	}

	@SuppressWarnings("unchecked")
	public JSONObject startRouting(
			double startLat, 
			double startLon, 
			double endLat,
			double endLon) {

    	log.debug("IN - startLat: " + startLat);
    	log.debug("IN - startLon: " + startLon);
    	log.debug("IN - endLat: " + endLat);
    	log.debug("IN - endLon: " + endLon);
    	
		JSONObject obj = new JSONObject();
		GHRequest req = new GHRequest(startLat, startLon, endLat, endLon)
				.setWeighting("fastest").setVehicle("car")
				.setLocale(Locale.GERMAN);
		GHResponse rsp = hopper.route(req);
		if (rsp.hasErrors()) {
			this.setPointList(null);
			this.setDistance(0.0);
			this.setTimeInMs(0);
			obj.put("Error", "No routing possible!");
		} else {
			this.setPointList(rsp.getPoints());
			this.setDistance(rsp.getDistance());
			this.setTimeInMs(rsp.getTime());
			obj.put("distance", this.getDistance());
			obj.put("timeInMs", this.getTimeInMs());
			obj.put("points", getPointListDoubles());
		}
		
		log.debug("OUT - json: " + obj);
		
		return obj;
	}
	
	public String getGhLocation() {
		return ghLocation;
	}

	public void setGhLocation(String ghLocation) {
		this.ghLocation = ghLocation;
	}

	public String getOsmFile() {
		return osmFile;
	}

	public void setOsmFile(String osmFile) {
		this.osmFile = osmFile;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public long getTimeInMs() {
		return timeInMs;
	}

	public void setTimeInMs(long timeInMs) {
		this.timeInMs = timeInMs;
	}

	public PointList getPointList() {
		return pointList;
	}
	
	private List<Double[]> getPointListDoubles() {
		return pointList.toGeoJson();
	}

	public void setPointList(PointList pointList) {
		this.pointList = pointList;
	}

}
