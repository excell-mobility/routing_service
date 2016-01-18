package routing;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import routingapi.RoutingService;

public class TestRoutingService {
	
	@Autowired
	private RoutingService routingService;

	@Before
	public void initialize() {
		routingService = new RoutingService(
				"src/test/resources/Dresden.osm.pbf",
				"src/test/resources/graphhopper");
	}
	
	@Test
	public void testRoutingService() {
		
		JSONObject startRouting = routingService.startRouting(51.048480, 13.729409, 51.049660, 13.74);
		assertTrue(startRouting.containsKey("distance"));
		assertTrue(startRouting.containsKey("points"));
		assertTrue(startRouting.containsKey("timeInMs"));
		
		startRouting = routingService.startRouting(60.048480, 21.729409, 51.049660, 13.74);
		assertTrue(startRouting.containsKey("Error"));
		
	}

}