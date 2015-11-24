package routingapi;

import java.util.List;
import java.util.Locale;

import org.json.simple.JSONObject;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;

public class RoutingService {

	private GraphHopper hopper;
	private double distance;
	private long timeInMs;
	private PointList pointList;

	public RoutingService() {
		hopper = new GraphHopper().forServer();
		hopper.setOSMFile("C:\\Users\\adm\\workspace\\Graphhopper\\Dresden.osm.pbf");
		hopper.setGraphHopperLocation("C:\\Users\\adm\\workspace\\Graphhopper\\graphhopper");
		hopper.setEncodingManager(new EncodingManager("car"));
		hopper.importOrLoad();
	}

	@SuppressWarnings("unchecked")
	public JSONObject startRouting(double startLat, double startLon, double endLat,
			double endLon) {

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
		return obj;
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
