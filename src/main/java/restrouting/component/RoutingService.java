package restrouting.component;

import java.util.List;
import java.util.Locale;

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

import restrouting.model.RoutingResponse;

@Component
public class RoutingService {

	private final Logger log;
	private GraphHopper hopper;
	
	private String osmFile;
	private String ghLocation;
	
	@Autowired
	public RoutingService(
			@Value("${routing.osmfile}") String osmFile,
			@Value("${routing.ghlocation}") String ghLocation) {
		
		log = LoggerFactory.getLogger(this.getClass());
		this.osmFile = osmFile;
		this.ghLocation = ghLocation;
		
		hopper = new GraphHopper().forServer();
		hopper.setOSMFile(this.getOsmFile());
		hopper.setGraphHopperLocation(this.getGhLocation());
		hopper.setEncodingManager(new EncodingManager("car"));
		hopper.importOrLoad();
	}

	public RoutingResponse startRouting(
			double startLat, 
			double startLon, 
			double endLat,
			double endLon) {
    	
		log.debug("IN - startLat: " + startLat);
    	log.debug("IN - startLon: " + startLon);
    	log.debug("IN - endLat: " + endLat);
    	log.debug("IN - endLon: " + endLon);
    	
		GHRequest req = new GHRequest(startLat, startLon, endLat, endLon)
				.setWeighting("fastest").setVehicle("car")
				.setLocale(Locale.GERMAN);
		GHResponse rsp = hopper.route(req);;
		
		if (rsp.hasErrors()) {
			log.error("No routing possible!");
			return null;
		}
		
		return new RoutingResponse(rsp.getDistance(),
				rsp.getTime(),
				getPointListDoubles(rsp.getPoints()));
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
	
	private List<Double[]> getPointListDoubles(PointList pointList) {
		return pointList.toGeoJson();
	}
}
