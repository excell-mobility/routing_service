package routing;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import routingapi.RoutingService;

public class TestRoutingService {
	
	private RoutingService routing;
	
	@Before
	public void initialize() {
		 routing = new RoutingService();
	}

	@Test
	public void testRoutingService() {
		
		JSONObject startRouting = routing.startRouting(51.048480, 13.729409, 51.049660, 13.74);
		assertTrue(startRouting.containsKey("distance"));
		assertTrue(startRouting.containsKey("points"));
		assertTrue(startRouting.containsKey("timeInMs"));
		
		startRouting = routing.startRouting(60.048480, 21.729409, 51.049660, 13.74);
		assertTrue(startRouting.containsKey("Error"));
		
	}

}